import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class GetPage extends RecursiveAction {
    private final String URL; //адрес сайта

    public GetPage(String url) {
        this.URL = url;
    }

    @Override
    protected void compute() {
        Main.countThreads.incrementAndGet();
        ArrayList<String> newUrls = Main.parsePage(this.URL);
        Main.countThreads.decrementAndGet();
        newUrls.forEach(Main::mapPageFork);
    }
}
