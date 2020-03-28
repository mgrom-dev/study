package com.example.taskmanager.repo;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.Storage;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
//класс запроса на сервер
class todo{
    private int id;
    private String name;
    private String description;
}

@RestController
@SuppressWarnings("unused")
public class ToDoController {


    //получаем список задач
    @RequestMapping(value = "todo", method = RequestMethod.GET, params = "list")
    public List<Task> list(@RequestParam("list") String list){
        return Storage.getAllToDo();
    }

    //добавляем задачу
    @RequestMapping(value = "todo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean add(@RequestBody Task todo){
        Storage.addToDo(new Task(todo.getName(), todo.getDescription()));
        return true;
    }

    //изменяем задачу
    @RequestMapping(value = "todo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean modify(@RequestBody Task todo){
        if (todo.getDescription() != null) {
            Storage.getById(todo.getId()).setDescription(todo.getDescription());
        }
        if (todo.getName() != null) {
            Storage.getById(todo.getId()).setName(todo.getName());
        }
        return true;
    }

    //удаляем задачу
    @RequestMapping(value = "todo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@RequestBody Task todo){
        Storage.deleteToDo(todo.getId());
        return true;
    }
}
