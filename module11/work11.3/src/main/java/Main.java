import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.ls.LSOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public final static String URL = "https://www.avnsk.com"; //адрес сайта, который нужно пропарсить
    public final static String PATH = "src/main/data/site.txt"; //путь к файлу для сохранения карты сайта
    public final static boolean USE_PROXY = true; //использовать прокси для доступа в интернет
    public final static Proxy PROXY = USE_PROXY ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.230.198", 3128)) : Proxy.NO_PROXY; //свойства прокси
    public final static int MAX_THREADS = 3; //максимальное количество парралельных потоков, которые будут парсить сайт на ссылки
    public static AtomicInteger countThreads = new AtomicInteger(1); //текущее количество парралельных потоков
    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("get_url"); //группа для создания новых потоков
    private final static String METHOD = "Threads"; //метод составления карты сайта
    private static SiteMap siteMap = new SiteMap(URL); //класс карты сайта

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        if ("Threads".equals(METHOD)) {
            //поиск с помощью паралельных потоков, где каждая найденная новая ссылка с сайта запускает новый паралельный поток для парсинга
            //почему-то данный метод работает быстрее, чем с ForkJoinPool
            mapPageThread(URL);
            while(THREAD_GROUP.activeCount() > 0) {
                Thread.onSpinWait();
            }
        } else {
            //поиск с помощью ForkJoinPool
            mapPageFork(URL);
        }

        System.out.println("Parsing done with: " + (System.currentTimeMillis() - start) + " ms. Find " + siteMap.getChildPagesUrl().size() + " pages.");

        //Записываем карту сайта в файл
        try(FileWriter writer = new FileWriter(PATH)) {
            writer.write(siteMap.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск ссылок с помощью ForkJoinPool
     */
    public static void mapPageFork(String url){
        while (countThreads.get() > MAX_THREADS) {
            Thread.onSpinWait();
        }
        GetPage getPage = new GetPage(url);
        new ForkJoinPool().invoke(getPage);
    }

    /**
     * Поиск ссылок с помощью обычных потоков
     */
    public static void mapPageThread(String url){
        while (countThreads.get() > MAX_THREADS) {
            Thread.onSpinWait();
        }
        new Thread(THREAD_GROUP, () -> {
            countThreads.incrementAndGet();
            ArrayList<String> newUrls = parsePage(url);
            countThreads.decrementAndGet();
            newUrls.forEach(Main::mapPageThread);
        }).start();
    }

    /**
     * @param url - адрес страницы, которую нужно пропарсить на ссылки
     * @return - возвращает список новых ссылок на страницы, которые уже добавлены в siteMap
     */
    public static ArrayList<String> parsePage(String url){
        ArrayList<String> newUrls = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).proxy(PROXY).maxBodySize(0).get();
            //получаем все ссылки которые ссылаются на адрес парсируемого сайта
            for (Element link : doc.select("a[href~=^" + siteMap.getUrl() + "|^/[a-z]]")){
                String cutLink = link.attributes().get("href"); //сокращенная ссылка (без адреса сайта)
                String fullLink = cutLink.matches("^" + siteMap.getUrl() + ".*") ? cutLink : siteMap.getUrl() + cutLink; //полная ссылка, вместе с адресом
                fullLink += fullLink.matches(".+/$") ? "" : "/"; //добавляем слэш в конец адреса
                //если ссылка успешно добавилась в карту сайта, значит это новая ссылка и ее нужно тоже пропарсить
                if (siteMap.addChildPage(fullLink)){
                    System.out.println(Thread.currentThread().getName() + ", найдена новая ссылка: " + fullLink);
                    newUrls.add(fullLink);
                }
            }
        } catch (HttpStatusException httpex){
            System.out.println("Page " + url + " error. " + httpex);
            //при ошибке когда страница сервера временно не доступна, пытаемся пропарсить ее еще раз
            if (httpex.getStatusCode() == 503) {
                newUrls.add(url);
            }
        } catch (IOException iex) {
            System.out.println("Page " + url + " not found. " + iex.getMessage());
        }
        return newUrls;
    }
}
