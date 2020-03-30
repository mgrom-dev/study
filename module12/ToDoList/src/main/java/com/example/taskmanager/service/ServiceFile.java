package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс, который осуществляет работу с файловой базой данных
 */
public class ServiceFile implements Base {
    private int counterId = 0; //счетчик для ид дела
    private static HashMap<Integer, Task> tasks = new HashMap<>(); //задачи
    private String fileTodo = ""; //путь к бд

    {
        Properties prop = new Properties();
        try {
            //Загружаем настройки
            prop.load(Objects.requireNonNull(getClass().getClassLoader().getResource("application.properties")).openStream());
            fileTodo = (String)prop.get("data.file");
            loadData();
            trackMainClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка данных из файла
     */
    @SneakyThrows
    private void loadData(){
        Files.readAllLines(Path.of(fileTodo)).forEach(line -> {
            String[] params = line.split(";|,\t");
            if (params.length == 1) {
                counterId = Integer.parseInt(params[0]);
            } else {
                Task todo = new Task(Integer.parseInt(params[0]), params[1], params[2]);
                tasks.put(todo.getId(), todo);
            }
        });
    }

    /**
     * Сохранение данных в файл
     */
    @SneakyThrows
    private void saveData(){
        String output = counterId + ";\n" + tasks.values().stream().
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

    @Override
    public List<Task> list() {
        return List.copyOf(tasks.values());
    }

    @Override
    public boolean add(Task task) {
        counterId++;
        task.setId(counterId);
        tasks.put(counterId, task);
        return true;
    }

    @Override
    public boolean modify(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.get(task.getId()).setName(task.getName());
            tasks.get(task.getId()).setDescription(task.getDescription());
            return true;
        }
        return false;
    }

    @Override
    public Task get(int id) {
        return tasks.get(id);
    }

    @Override
    public boolean delete(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            return true;
        }
        return false;
    }
}