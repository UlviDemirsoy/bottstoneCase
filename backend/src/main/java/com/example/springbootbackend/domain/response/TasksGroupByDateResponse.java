package com.example.springbootbackend.domain.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TasksGroupByDateResponse {

    private Date dateinfo;
    private int resourcesInUse = 0;
    public void increaseResource(int amount){
        resourcesInUse += amount;
    }
}
