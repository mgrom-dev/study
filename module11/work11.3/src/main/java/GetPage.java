import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.RecursiveAction;

public class GetPage extends RecursiveAction {
    private final String URL; //адрес сайта
    private final Proxy PROXY; //прокси
    private static SiteMap siteMap; //класс карты сайта, для сохранения результатов

    public void setSiteMap(SiteMap siteMap){
        GetPage.siteMap = siteMap;
    }

    public GetPage(String url, Proxy proxy) {
        this.URL = url;
        this.PROXY = proxy;
    }

    @Override
    protected void compute() {
        try {
            Document doc = Jsoup.connect(URL).proxy(PROXY).maxBodySize(0).get();
            for (Element link : doc.select("a[href~=^" + siteMap.getUrl() + "|^/[a-z]]")){
                String cutLink = link.attributes().get("href");
                String fullLink = cutLink.matches("^" + siteMap.getUrl() + ".*") ? cutLink : siteMap.getUrl() + cutLink;
                fullLink += fullLink.matches(".+/$") ? "" : "/";
                if (siteMap.addChildPage(fullLink)){
                    System.out.println(Thread.currentThread().getName() + ", найдена новая ссылка: " + fullLink);
                    GetPage task = new GetPage(fullLink, PROXY);
                    task.fork(); // запустим асинхронно
                    task.join();
                }
            }
        } catch (HttpStatusException httpex){
            System.out.println("Page " + URL + " error. " + httpex);
            if (httpex.getStatusCode() == 503) {
                this.invoke();
            }
        } catch (IOException iex) {
            System.out.println("Page " + URL + " not found. " + iex.getMessage());
        }
    }
}
