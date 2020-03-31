package com.example.taskmanager.repo;

import com.example.taskmanager.Main;
import com.example.taskmanager.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "todo")
@SuppressWarnings("unused")
public class ToDoController {
    //получаем задачу по ид
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getPolls(@PathVariable int id){
        return ResponseEntity.ok(Main.base.get(id));
    }

    //получаем список задач
    @GetMapping(params = "list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(Main.base.list());
    }

    //добавляем задачу
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Task todo){
        Main.base.add(todo);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //изменяем задачу
    @PutMapping
    public ResponseEntity<?> modify(@RequestBody Task todo){
        Main.base.modify(todo);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //удаляем задачу
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam int id){
        Main.base.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
