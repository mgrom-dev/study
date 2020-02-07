import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Класс карты сайта, который содержит ссылку сайта и уникальные ссылки на дочерние страницы
 */
public class SiteMap {
    private String url; //адрес сайта
    private HashSet<String> childPagesUrl; //дочерние страницы

    /**
     * в конструторе инициализируем дочерние страницы и добавляем туда ссылку на главный адрес сайта
     */
    public SiteMap(String url) {
        this.url = url;
        childPagesUrl = new HashSet<>();
        childPagesUrl.add(url + "/");
    }

    public String getUrl() {
        return url;
    }

    /**
     * Метод проверяем существует ли уже данная ссылка в дочерних страницах и если ее нет, то добавляет
     * @param url - адрес добавляемого сайта
     * @return - возвращаем истину, если добавили новую ссылку
     */
    public boolean addChildPage(String url){
        if (childPagesUrl.contains(url)) {
            return false;
        }
        childPagesUrl.add(url);
        return true;
    }

    public HashSet<String> getChildPagesUrl() {
        return childPagesUrl;
    }

    /**
     * Меняем метод получения строкового представления объекта, который сортирует и форматирует ссылки на дочерние страницы
     */
    @Override
    public String toString() {
        List<String> sortedList = new ArrayList<>(childPagesUrl);
        Collections.sort(sortedList);
        StringBuilder treeMap = new StringBuilder();
        for (String page : sortedList){
            int tab = page.replaceAll("[^/]", "").length() - 3;
            treeMap.append("\t".repeat(tab)).append(page).append("\n");
        }
        return treeMap.toString();
    }
}
