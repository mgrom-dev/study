package main;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import response.ToDo;

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
    public List<ToDo> list(@RequestParam("list") String list){
        return Storage.getAllToDo();
    }

    //добавляем задачу
    @RequestMapping(value = "todo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean add(@RequestBody todo todo){
        Storage.addToDo(new ToDo(todo.getName(), todo.getDescription()));
        return true;
    }

    //изменяем задачу
    @RequestMapping(value = "todo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean modify(@RequestBody todo todo){
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
    public boolean delete(@RequestBody todo todo){
        Storage.deleteToDo(todo.getId());
        return true;
    }
}
