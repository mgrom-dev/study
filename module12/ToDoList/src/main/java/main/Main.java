package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import response.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main  {
    public static final String fileTodo = "src/main/resources/todo.txt";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        try {
            //загрузка списка дел из файла
            Files.readAllLines(Path.of(fileTodo)).forEach(line -> {
                String[] params = line.split(";|,\t");
                if (params.length == 1) {
                    Storage.setCounterId(Integer.parseInt(params[0]));
                } else {
                    ToDo todo = new ToDo(params[1], params[2]);
                    todo.setId(Integer.parseInt(params[0]));
                    Storage.getAllToDo().add(todo);
                }
            });

            System.out.println("Введите: exit, для закрытия сервера.");
            while(!new Scanner(System.in).nextLine().equals("exit"));

            //сохранение списка дел в файл
            String output = Storage.getCounterId() + ";\n" + Storage.getAllToDo().stream().
                    map(todo -> todo.getId() + ",\t" + todo.getName() + ",\t" + todo.getDescription()).
                    collect(Collectors.joining(";\n"));
            Files.writeString(Path.of(fileTodo), output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
