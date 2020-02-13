package main;

import response.ToDo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Storage {
    private static ArrayList<ToDo> toDo = new ArrayList<>();
    private static int counterId = 0;

    public static List<ToDo> getAllToDo(){
        return toDo;
    }

    public static int getCounterId() {
        return counterId;
    }

    public static void setCounterId(int counterId) {
        Storage.counterId = counterId;
    }

    //добавляем задачу
    public static synchronized int addToDo(ToDo toDo){
        counterId++;
        Storage.toDo.add(toDo);
        toDo.setId(counterId);
        return counterId;
    }

    //удаляем задачу
    public static synchronized void deleteToDo(int id){
        ToDo deleteItem = getById(id);
        if (deleteItem != null) {
            toDo.remove(deleteItem);
        }
    }

    //получить задачу по ид
    public static ToDo getById(int id){
        for (ToDo item : toDo){
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
