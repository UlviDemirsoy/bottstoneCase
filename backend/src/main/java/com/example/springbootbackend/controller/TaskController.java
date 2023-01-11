package com.example.springbootbackend.controller;


import com.example.springbootbackend.model.Task;
import com.example.springbootbackend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
        return new ResponseEntity<Task>(taskService.saveTask(task), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id){
        return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id){
        taskService.deleteTask(id);
        return new ResponseEntity<String>("Task deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task){
        return new ResponseEntity<Task>(taskService.updateTask(task,id), HttpStatus.OK);
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

}
