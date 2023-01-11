package com.example.springbootbackend.service;


import com.example.springbootbackend.domain.response.TasksGroupByDateResponse;
import com.example.springbootbackend.model.Task;


import java.util.Date;
import java.util.List;

public interface TaskService {

    Task saveTask(Task task);
    List<Task> getAllTasks();
    Task getTaskById(long id);
    Task updateTask(Task task, long id);
    void deleteTask(long id);

    List<TasksGroupByDateResponse> getResourceInfo(Date start, Date end, String type);


}
