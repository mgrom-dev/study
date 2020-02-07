import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public final static String URL = "https://www.avnsk.com/"; //адрес сайта, который нужно пропарсить
    public final static String PATH = "src/main/data/site.txt"; //путь к файлу для сохранения карты сайта
    public final static boolean USE_PROXY = true; //использовать прокси для доступа в интернет
    public final static Proxy PROXY = USE_PROXY ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.230.198", 3128)) : Proxy.NO_PROXY; //свойства прокси
    private static SiteMap siteMap = new SiteMap(URL); //класс карты сайта
    private static ThreadGroup threadGroup = new ThreadGroup("get_url"); //группа для создания новых потоков
    private static String method = "Threads"; //метод составления карты сайта

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        if ("Threads".equals(method)) {
            //поиск с помощью паралельных потоков, где каждая найденная новая ссылка с сайта запускает новый паралельный поток для парсинга
            //почему-то данный метод работает быстрее, чем с ForkJoinPool
            mapPageThread(URL);
            while(threadGroup.activeCount() > 0) {
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
        GetPage getPage = new GetPage(url, PROXY);
        getPage.setSiteMap(siteMap);
        new ForkJoinPool().invoke(getPage);
    }

    /**
     * Поиск ссылок с помощью кучи параллельных потоков
     */
    public static void mapPageThread(String url){
        //создаем новый паралельный поток
        new Thread(threadGroup, ()->{
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
                        mapPageThread(fullLink);
                    }
                }
            } catch (HttpStatusException httpex){
                System.out.println("Page " + url + " error. " + httpex);
                //при ошибке когда страница сервера временно не доступна, пытаемся пропарсить ее еще раз
                if (httpex.getStatusCode() == 503) {
                    mapPageThread(url);
                }
            } catch (IOException iex) {
                System.out.println("Page " + url + " not found. " + iex.getMessage());
            }
        }).start();
    }
}
