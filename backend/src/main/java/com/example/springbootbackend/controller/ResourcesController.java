package com.example.springbootbackend.controller;



import com.example.springbootbackend.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*")
public class ResourcesController {
    private TaskService taskService;

    public ResourcesController(TaskService taskService) {
        this.taskService = taskService;
    }



    @GetMapping
    @ResponseBody
    public ResponseEntity getResourceInformationByDate(@RequestParam(name = "type") String type,
                                                       @RequestParam(name= "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                       @RequestParam(name= "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {

        return new ResponseEntity<>(taskService.getResourceInfo(start, end, type ), HttpStatus.OK);

    }




}
