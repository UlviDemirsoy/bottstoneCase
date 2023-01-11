package com.example.springbootbackend.repository;
import com.example.springbootbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query( value = "SELECT id, created_date, name, project_id, start, end, resource_per_day FROM tasks t WHERE t.end >= :start and t.start <= :end",
    nativeQuery=true)
    List<Task> getAllTasksWithDateInfo(@Param("start") Date start, @Param("end") Date end);

}
