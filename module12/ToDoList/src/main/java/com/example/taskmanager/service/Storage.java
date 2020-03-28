package com.example.taskmanager.service;

import com.example.taskmanager.model.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Storage {

    private static ArrayList<Task> taskM = new ArrayList<>();
    private static int counterId = 0;

    public static List<Task> getAllToDo(){
        return taskM;
    }

    public static int getCounterId() {
        return counterId;
    }

    public static void setCounterId(int counterId) {
        Storage.counterId = counterId;
    }

    //добавляем задачу
    public static synchronized int addToDo(Task taskM){
        counterId++;
        Storage.taskM.add(taskM);
        taskM.setId(counterId);
        return counterId;
    }

    //удаляем задачу
    public static synchronized void deleteToDo(int id){
        Task deleteItem = getById(id);
        if (deleteItem != null) {
            taskM.remove(deleteItem);
        }
    }

    //получить задачу по ид
    public static Task getById(int id){
        for (Task item : taskM){
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
