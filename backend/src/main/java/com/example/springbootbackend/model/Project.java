package com.example.springbootbackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Projects")
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "start")
    private java.sql.Date start;

    @Column(name = "end")
    private java.sql.Date end;

}
