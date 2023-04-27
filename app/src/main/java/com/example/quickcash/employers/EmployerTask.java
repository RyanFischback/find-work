package com.example.quickcash.employers;

import com.example.quickcash.Task;

/**
 * Where the Employer will be able to edit the various tasks created
 */

public class EmployerTask extends Task {


    private String taskName;
    private String taskDescription;
    private boolean complete;

    public EmployerTask(String taskName, String taskDescription){
        super(taskName,taskDescription);
        complete=false;
    }

    /**
     * Employer can delete tasks
     */
    public void deleteTask(){


    }


    /**
     * This is where the task gets set as completed
     */
    public void setComplete(){
        this.complete=true;
    }


    /**
     * Employer can edit tasks
     */
    public void editTaskDescription(String newDescription){
        this.taskDescription=newDescription;
    }

    /**
     * 
     * @param newTaskName
     */
    public void setTaskName(String newTaskName){
        this.taskName=newTaskName;
    }


}
