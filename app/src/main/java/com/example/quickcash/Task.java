package com.example.quickcash;

/**
 * This class is where the tasks will be created
 */
public abstract class Task {

    private String taskName;
    private String taskDescription;



    public Task(String taskName, String taskDescription){
        this.taskName=taskName;
        this.taskDescription=taskDescription;
    }

}
