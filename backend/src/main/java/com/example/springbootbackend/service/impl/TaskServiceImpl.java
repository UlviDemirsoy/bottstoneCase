package com.example.springbootbackend.service.impl;

import com.example.springbootbackend.domain.response.TasksGroupByDateResponse;
import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Task;
import com.example.springbootbackend.repository.TaskRepository;
import com.example.springbootbackend.service.TaskService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }


    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "Id", id) );
    }

    @Override
    public Task updateTask(Task task, long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "Id", id));

        existingTask.setName(task.getName());
        existingTask.setResourcePerDay(task.getResourcePerDay());
        existingTask.setProjectId(task.getProjectId());
        existingTask.setEnd(task.getEnd());
        existingTask.setStart(task.getStart());


        taskRepository.save(existingTask);
        return existingTask;
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "Id", id));
        taskRepository.deleteById(id);
    }

    @Override
    public List<TasksGroupByDateResponse> getResourceInfo(Date start, Date end, String type) {
        List<Task> mytasks = taskRepository.getAllTasksWithDateInfo(start,end);

        LocalDate startLocal = Instant.ofEpochMilli(start.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate endLocal = Instant.ofEpochMilli(end.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        List<TasksGroupByDateResponse> totalDaysWithResourceInfo = new ArrayList<>();

        while (!startLocal.isAfter(endLocal)) {
            totalDaysWithResourceInfo.add(new TasksGroupByDateResponse(Date.from(startLocal.atStartOfDay
                    (ZoneId.systemDefault()).toInstant()),0));
            startLocal = startLocal.plusDays(1);
        }



        for (Task t :mytasks) {
            Date taskStart = t.getStart();
            Date taskEnd = t.getEnd();

            if(taskStart.compareTo(start) < 0){
                taskStart = start;
            }
            if(taskEnd.compareTo(end) > 0){
                taskEnd = end;
            }


            int startIndex = (int) TimeUnit.DAYS.convert( taskStart.getTime() - start.getTime(), TimeUnit.MILLISECONDS);
            int loopCount=  (int) TimeUnit.DAYS.convert( taskEnd.getTime() -taskStart.getTime()  , TimeUnit.MILLISECONDS)+1;
            while(loopCount>0){
                TasksGroupByDateResponse myobj = totalDaysWithResourceInfo.get(startIndex);
                myobj.increaseResource(t.getResourcePerDay());
                startIndex += 1;
                loopCount -= 1;
            }
        }

        if("day".equals(type)){
            return totalDaysWithResourceInfo;
        }else if ("week".equals(type)) {

            LocalDate startLocal2 = Instant.ofEpochMilli(start.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate endLocal2 = Instant.ofEpochMilli(end.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            List<TasksGroupByDateResponse> totalWeeksByResourceInfo = new ArrayList<>();

            while (startLocal2.compareTo(endLocal2) < 0) {
                totalWeeksByResourceInfo.add(new TasksGroupByDateResponse(Date.from(startLocal2.atStartOfDay
                        (ZoneId.systemDefault()).toInstant()),0));
                startLocal2 = startLocal2.plusWeeks(1);
            }


            int startingIndex = 0;
            int k = 0;
            for (TasksGroupByDateResponse t:totalDaysWithResourceInfo
                 ) {
                TasksGroupByDateResponse myobj = totalWeeksByResourceInfo.get(startingIndex);
                myobj.increaseResource(t.getResourcesInUse());
                k ++;
                if(k%7 == 0){
                    startingIndex ++;
                }
            }

            return totalWeeksByResourceInfo;
            
        } else if ("month".equals(type)) {

            LocalDate startLocal2 = Instant.ofEpochMilli(start.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate endLocal2 = Instant.ofEpochMilli(end.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            List<TasksGroupByDateResponse> totalMonthsByResourceInfo = new ArrayList<>();

            while (startLocal2.compareTo(endLocal2) < 0) {
                totalMonthsByResourceInfo.add(new TasksGroupByDateResponse(Date.from(startLocal2.atStartOfDay
                        (ZoneId.systemDefault()).toInstant()),0));
                startLocal2 = startLocal2.plusMonths(1);
            }


            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            int month = cal.get(Calendar.MONTH);
            int startingIndex = 0;
            for (TasksGroupByDateResponse t:totalDaysWithResourceInfo
            ) {
                TasksGroupByDateResponse myobj = totalMonthsByResourceInfo.get(startingIndex);
                myobj.increaseResource(t.getResourcesInUse());


                cal.setTime(t.getDateinfo());
                int tMonth = cal.get(Calendar.MONTH);
                if(month != tMonth ){
                    month = tMonth;
                    startingIndex++;
                }
            }

            return  totalMonthsByResourceInfo;
        }



        return null;

    }
}
