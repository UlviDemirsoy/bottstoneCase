package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.Project;
import com.example.springbootbackend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> saveTask(@RequestBody Project project){
        return new ResponseEntity<Project>(projectService.saveProject(project), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getTaskById(@PathVariable("id") long id){
        return new ResponseEntity<Project>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id){
        projectService.deleteProject(id);
        return new ResponseEntity<String>("Project deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Project> updateTask(@PathVariable("id") long id, @RequestBody Project project){
        return new ResponseEntity<Project>(projectService.updateProject(project,id), HttpStatus.OK);
    }

    @GetMapping
    public List<Project> getAllTasks(){
        return projectService.getAllProject();
    }


}
