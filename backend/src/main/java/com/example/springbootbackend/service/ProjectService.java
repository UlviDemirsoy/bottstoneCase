package com.example.springbootbackend.service;

import com.example.springbootbackend.model.Project;

import java.util.List;

public interface ProjectService {
    Project saveProject(Project project);
    List<Project> getAllProject();
    Project getProjectById(long id);
    Project updateProject(Project project, long id);
    void deleteProject(long id);
}
