import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static String url = "lenta.ru"; //адрес страницы
    public static String pathToSaveImg = "img"; //путь к папке для сохранения картинок

    public static void main(String[] args) {
        //добавляем протокол http, если в url он не указан
        if (!url.matches("https?://.+")) {
            url = "http://" + url;
        }
        try {
            //создаем директорию под сохранение картинок
            if (!Files.exists(Paths.get(pathToSaveImg))) {
                Files.createDirectory(Paths.get(pathToSaveImg));
            }
            //получаем страницу с сайта в формате document и выбираем все теги изображения
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            System.out.println("Список загруженных изображений:");
            doc.select("img").forEach(i-> {
                //получаем адрес изображения
                String imgUrl = i.attr("src");
                loadImg(imgUrl, pathToSaveImg);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод загружает изображение в указанную папку
     * @param url - путь к изображению
     * @param path - путь к папке, в которую будет сохранено изображение
     */
    public static void loadImg(String url, String path) {
        try {
            //получаем имя изображения из пути url и расширение файла
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
            //скачиваем только картинки
            if ("jpg".equals(ext) || "png".equals(ext) || "bmp".equals(ext)) {
                //загружаем картинку и сохраняем в файл
                byte[] bytes = Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes();
                Files.write(Paths.get(path + "/" + fileName), bytes);
                System.out.println(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
