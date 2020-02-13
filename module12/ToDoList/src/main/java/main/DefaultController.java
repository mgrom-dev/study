package main;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.UnknownHostException;
import java.util.Map;

@Controller
@RequestMapping("/")
@SuppressWarnings("unused")
public class DefaultController {
    //базовый адрес, перенаправление на index.html
    @GetMapping
    public String home() {
        return "redirect:/index";
    }

    //адрес домашней страницы index
    @GetMapping(value = "/index{file_name:.htm|.html|$}")
    public String index(@PathVariable("file_name") String fileName, @RequestParam Map<String, String> allRequestParams) {
        if (!fileName.isEmpty() || allRequestParams.size() > 0) { //если адрес страницы index.htm или index.html, перенаправляем на index
            return "redirect:/index";
        }
        return "index";
    }

    //получаем текущую погоду
    @GetMapping(value = "/weather{file_name:.jpg|.png|$}")
    public String weather(@PathVariable("file_name") String fileName) {
        String resourcePath = "src/main/webapp/WEB-INF/resources/images/weather/"; //место хранения картинок с погодой
        long now = System.currentTimeMillis(); //текущее время
        String weatherImg = ""; //путь к картинке с погодой
        try {
            weatherImg = Files.walk(Paths.get(resourcePath)).filter(path -> {
                //фильтруем все картинки которые старше двух часов
                String time = path.getFileName().toString().replaceAll("[^0-9]", "");
                if (!time.isEmpty()) {
                    return now - (1000 * 60 * 60 * 2) < Long.parseLong(time);
                }
                return false;
            }).findFirst().orElse(Paths.get("")).getFileName().toString(); //берем первую ликвидную картинку
            //если свежой картинки нет в базе, то загружаем новую с сайта bookcdn и сохраняем в хранилище
            if (weatherImg.isEmpty()) {
                byte[] bytes = Jsoup.connect("https://w.bookcdn.com/weather/picture/11_17760_1_20_e0dfd9_118_45505c_607994_f0edf0_0_e0dfd9_607994_0_6.png?scode=2&domid=589&anc_id=23426").
                        ignoreContentType(true).execute().bodyAsBytes();
                Files.write(Paths.get(resourcePath + now + ".png"), bytes);
                weatherImg = now + ".png";
            }
        } catch (Exception e) {
            System.out.println("Error in 'DefaultController.java' method get weather: " + e.toString());
        }
        if (!weatherImg.isEmpty()) {
            return "redirect:/images/weather/" + weatherImg;
        }
        return null;
    }

    //адрес страницы с редактированием списка дел
    @GetMapping(value = "/todo{file_name:.htm|.html|$}")
    public String todo(@PathVariable("file_name") String fileName, @RequestParam Map<String, String> allRequestParams) {
        if (!fileName.isEmpty() || allRequestParams.size() > 0) {
            return "redirect:/todo";
        }
        return "todo";
    }

    //адрес страницы с редактором регулярных выражений
    @GetMapping(value = "/regexp{file_name:.htm|.html|$}")
    public String regExp(@PathVariable("file_name") String fileName, @RequestParam Map<String, String> allRequestParams) {
        if (!fileName.isEmpty() || allRequestParams.size() > 0) {
            return "redirect:/regexp";
        }
        return "regexp";
    }
}
