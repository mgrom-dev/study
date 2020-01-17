import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Настраиваем JSONObject из json-simple под нужды парсинга вики страницы со станциями Московского метро
 * Будут касты с {(JSONObject), (JSONArray)}, выключаем предупреждения об анчекед
 */
@SuppressWarnings("unchecked")
public class wikiMskMetroJSON extends JSONObject
{
    JSONObject stations = new JSONObject(); //станции метро

    JSONArray lines = new JSONArray(){ //линии метро
        @Override
        public boolean contains(Object o) {
            for (Object line : this) {
                if (((JSONObject) line).get("number").equals(o)) return true;
            }
            return false;
        }
    };

    JSONArray connections = new JSONArray(){ //станции перехода
        //получаем числовое значения соединений, для сравнения
        @SuppressWarnings("unempty")
        public int getHash(JSONArray connects) {
            int sum = 0;
            for (Object connect : connects){
                sum += ((String)((JSONObject) connect).get("line")).chars().reduce(Integer::sum).orElse(0);
                sum += ((String)((JSONObject) connect).get("station")).chars().reduce(Integer::sum).orElse(0);
            }
            return sum;
        }
        //меняем проверку на существование аналогичной записи
        @Override
        public boolean contains(Object o) {
            int hash = getHash((JSONArray) o);
            for (Object connects : this){
                if (hash == getHash((JSONArray) connects)) return true;
            }
            return false;
        }
    };

    /**
     * @param url - путь к интернет странице вики с станциями метро
     */
    @SuppressWarnings("unused")
    public void parseURL(String url)
    {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            parseDoc(doc);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath - путь к файлу html страницы вики с станциями метро на компьютере
     */
    @SuppressWarnings("unused")
    public void parseFile(String filePath)
    {
        try {
            Document doc = Jsoup.parse(new File(filePath), "UTF8");
            parseDoc(doc);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param doc - документ страницы вики со станциями метро
     */
    private void parseDoc(Document doc)
    {
        Elements lines = doc.select(".standard.sortable tr");
        lines.forEach(line -> {
            Elements cells = line.getElementsByTag("td"); //все ячейки текущей строки в таблице
            if (cells.size() == 8 || cells.size() == 7) {
                getDataFromLine(cells);
            }
        });
        this.put("stations", stations);
        this.put("lines", this.lines);
        this.put("connections", connections);
    }

    /**
     * получаем данные по станции (название, №ветки, название ветки, цвет линий и переходы)
     * @param cells - ячейки в текущей строке таблицы со станциями и переходами
     */
    private void getDataFromLine(Elements cells)
    {
        Element elStationName = cells.get(1).select("a").first(); //название станции будем брать из текста ссылки
        Element elStationLine = cells.get(0).select("img").last(); //линию метро на которой расположена станция будем брать из ссылки на изображение
        Elements elConnection = cells.get(3).select("img"); //станции перехода на другую ветку метро будем брать из изображении
        //проверяем существование данных по станции
        if (elStationName != null && elStationLine != null)
        {
            String stationName = elStationName.text(); //имя станции
            String lineName = elStationLine.attr("alt"); //имя линии
            String lineNumber = getNumberLineFromUrl(elStationLine.attr("src")); //номер линии
            String lineColor = getColorLine(cells.get(0).attr("style")); //цвет линии
            putStation(lineNumber, stationName);
            putLine(lineNumber, lineName, lineColor);
            putConnections(elConnection, lineNumber, stationName);
        }
    }

    /**
     * @param color текст стиля с цветом (background:#...)
     * @return - возвращает цвет линии
     */
    private static String getColorLine(String color)
    {
        Matcher lineColor = Pattern.compile("(?<=:#).+").matcher(color);
        if (lineColor.find()) {
            return lineColor.group();
        }
        return "WHITE";
    }

    /**
     * @param url - адрес изображения с веткой метро
     * @return - получаем №ветки метро из url пути изображения
     */
    private static String getNumberLineFromUrl(String url)
    {
        url = url.substring(url.lastIndexOf("Moskwa_Metro_Line_") + 18).split(".svg.")[0];
        try {
            url = java.net.URLDecoder.decode(url, StandardCharsets.UTF_8.name()); //декодируем URL из ASCII
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * добавляем переходы на другие станции в JSON
     * @param elements - изображения веток метро, откуда будем брать название станции и №ветки метро
     * @param line - №ветки для которой ищутся соединения
     * @param name - имя станции для которой ищутся соединения
     */
    private void putConnections(Elements elements, String line, String name)
    {
        StringBuilder connectionsJSON = new StringBuilder("[{\"line\":\"" + line + "\", \"station\":\"" + name + "\"}");
        for (Element el: elements)
        {
            Matcher match = Pattern.compile("(?<=Переход на станцию )[А-Я а-я]+(?= [А-Я])").matcher(el.attr("alt"));
            line = getNumberLineFromUrl(el.attr("src"));
            if (line.length() != 0 && match.find()) {
                connectionsJSON.append(",{\"line\":\"").append(line).append("\", \"station\":\"").append(match.group()).append("\"}");
            }
        }
        JSONArray connects = (JSONArray) JSONValue.parse(connectionsJSON.append("]").toString());
        if (connects.size() > 1 && !connections.contains(connects)) {
            connections.add(connects);
        }
    }

    /**
     * добавляем ветку метро в JSON
     * @param number - №ветки
     * @param name - название ветки
     * @param color - цвет ветки
     */
    private void putLine(String number, String name, String color)
    {
        if (number.length() != 0 && name.length() != 0 && color.length() != 0) {
            if (!lines.contains(number)) {
                lines.add(JSONValue.parse(new Formatter().format("{\"number\":\"%s\", \"name\":\"%s\", \"color\":\"%s\"}",
                        number, name, color).toString()));
            }
        }
    }

    /**
     * добавляем станцию метро в JSON
     * @param line - №ветки
     * @param name - название станции
     */
    private void putStation(String line, String name)
    {
        if (line.length() != 0 && name.length() != 0) {
            if (!stations.containsKey(line)) { stations.put(line, new JSONArray()); }
            ((JSONArray) stations.get(line)).add(name);
        }
    }
}