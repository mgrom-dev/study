package com.example.taskmanager.repo;

import com.example.taskmanager.Main;
import com.example.taskmanager.model.Task;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping(value = "/weather{file_name:.htm|.html|$}", produces = "image/jpg")
    public ResponseEntity<byte[]> weather(@PathVariable("file_name") String fileName) {
        try {
            byte[] bytes = Jsoup.connect("https://w.bookcdn.com/weather/picture/11_17760_1_20_e0dfd9_118_45505c_607994_f0edf0_0_e0dfd9_607994_0_6.png?scode=2&domid=589&anc_id=23426").
                    ignoreContentType(true).execute().bodyAsBytes();
            return new ResponseEntity<>(bytes, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in 'DefaultController.java' method get weather: " + e.toString());
        }
        return null;
    }

    //адрес страницы с редактированием списка дел
    @GetMapping(value = "/todo{file_name:.htm|.html|$}")
    public String todo(@PathVariable("file_name") String fileName, @RequestParam Map<String, String> allRequestParams, Model model) {
        if (!fileName.isEmpty() || allRequestParams.size() > 0) {
            return "redirect:/todo";
        }
        ArrayList<Task> tasks = new ArrayList<>(Main.base.list());
        model.addAttribute("tasks", tasks);
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
