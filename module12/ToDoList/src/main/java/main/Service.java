package main;

import lombok.SneakyThrows;
import response.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс, который осуществляет работу с базой данных
 */
public class Service {

    private String fileTodo = "";

    {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResource("application.properties").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileTodo = (String)prop.get("data.file");

        loadData();
        trackMainClass();
    }

    /**
     * Загрузка данных из файла
     */
    @SneakyThrows
    private void loadData(){
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
    }

    /**
     * Сохранение данных в файл
     */
    @SneakyThrows
    private void saveData(){
        String output = Storage.getCounterId() + ";\n" + Storage.getAllToDo().stream().
                map(todo -> todo.getId() + ",\t" + todo.getName() + ",\t" + todo.getDescription()).
                collect(Collectors.joining(";\n"));
        Files.writeString(Path.of(fileTodo), output);
    }

    /**
     * Таймер с отслеживанием завершения главного потока
     */
    private void trackMainClass(){
        //запускаем таймер с периодичность 1 сек.
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //если поток с главным классом закрыт, то производим сохранение бд и выходим из программы.
                if (Thread.getAllStackTraces().keySet().stream().noneMatch(key -> "main".equals(key.getName()))) {
                    saveData();
                    System.exit(0);
                }
            }
        },0, 1, TimeUnit.SECONDS);
    }
}