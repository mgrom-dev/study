package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

/**
 * Интерфейс для работы с БД
 */
public interface Base {
    //Список задач
    List<Task> list();

    //Добавить новую задачу
    boolean add(Task task);

    //Изменить задачу
    boolean modify(Task task);

    //Получить задачу по ид
    Task get(int id);

    //Удаление задачи
    boolean delete(int id);
}
