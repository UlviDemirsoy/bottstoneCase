package com.example.springbootbackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="projectId")
    private int projectId;

    @Column(name="resourcePerDay")
    private int resourcePerDay;

    @Column(name = "start")
    private java.util.Date start;

    @Column(name = "end")
    private java.util.Date end;

    @Column(name="created_date")
    private java.util.Date createdDate;

}
