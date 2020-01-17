// Написать код парсинга страницы Википедии “Список станций Московского метрополитена”,
// который будет на основе этой страницы создавать JSON-файл со списком станций
// по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json, приложен к домашнему заданию)
// * Также пропарсить и вывести в JSON-файл переходы между станциями.
// Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static final String url = "https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена";
    //public static final String url = "src/data/wiki.html";
    public static final String fileName = "src/data/moscow_map.json";
    public static wikiMskMetroJSON map = new wikiMskMetroJSON();

    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        //парсим страничку вики со станциями и пишем в файл
        //map.parseFile(url);
        map.parseURL(url);
        try(FileWriter writer = new FileWriter(fileName)) {
            // так как lines и connections созданы через анонимный класс и принадлежат классу wikiMskMetroJSON,
            // Gson с ними не может работать с ними, поэтому делаем конвертацию и собираем заново JSON
            JsonObject json = new Gson().fromJson(map.toString(), JsonObject.class);
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(json));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //читаем с файла JSON со станциями и выводим в консоль количество станции на каждой линии
        try {
            JSONObject fileJSON = (JSONObject) JSONValue.parse(Files.readString(Paths.get(fileName)));
            for (String numberLine : (Iterable<String>) ((JSONObject) fileJSON.get("stations")).keySet()) {
                int countStations = ((JSONArray) ((JSONObject) fileJSON.get("stations")).get(numberLine)).size();
                String nameLine = getNameLine(numberLine, fileJSON);
                System.out.println(nameLine + " № " + numberLine + ", количество станции на ветке: " + countStations);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @param numberLine номер линии для которой нужно найти имя
    // @return имя ветки
    private static String getNameLine(String numberLine, JSONObject json){
        for (Object line : (JSONArray) json.get("lines")) {
            if (((JSONObject) line).get("number").equals(numberLine)){
                return ((JSONObject) line).get("name").toString();
            }
        }
        return "";
    }
}
