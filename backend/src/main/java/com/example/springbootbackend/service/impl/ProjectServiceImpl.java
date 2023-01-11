package com.example.springbootbackend.service.impl;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Project;
import com.example.springbootbackend.repository.ProjectRepository;
import com.example.springbootbackend.service.ProjectService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }


    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "Id", id) );
    }

    @Override
    public Project updateProject(Project project, long id) {
        Project existingProject = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project", "Id", id));

        existingProject.setName(project.getName());
        existingProject.setEnd(project.getEnd());
        existingProject.setStart(project.getStart());

        projectRepository.save(existingProject);
        return existingProject;
    }

    @Override
    public void deleteProject(long id) {
        projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project", "Id", id));
        projectRepository.deleteById(id);
    }
}
