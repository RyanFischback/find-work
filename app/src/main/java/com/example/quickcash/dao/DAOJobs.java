package com.example.quickcash.dao;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.quickcash.Job;
import com.example.quickcash.User;
import com.example.quickcash.employees.EmployeeJobsActivity;
import com.example.quickcash.employees.EmployeeJobsList;
import com.example.quickcash.employees.EmployeeJobsListMap;
import com.example.quickcash.employees.EmployeeTakenJobsList;
import com.example.quickcash.employers.EmployerCompleted;
import com.example.quickcash.employers.EmployerJobsActivity;
import com.example.quickcash.employers.EmployerPosted;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a database object for Jobs using firebase.
 * Queries are created here to interact with firebase and save our users data
 *
 */
public class DAOJobs {

    private DatabaseReference dbRef;
    private DatabaseReference refEmployee;
    private DatabaseReference refEmployer;

    /**
     * Constructor for DAOJobs (Connection with Firebase setup)
     */
    public DAOJobs(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(Job.class.getSimpleName());
        refEmployee = db.getReferenceFromUrl("https://quick-cash-4d952-default-rtdb.firebaseio.com/messages/employee");
        refEmployer = db.getReferenceFromUrl("https://quick-cash-4d952-default-rtdb.firebaseio.com/messages/employer");
    }

    /**
     * This method creates a new Job for an employer
     * @param job Job object from user
     * @return
     */
    public Task<Void> PostJob(Job job)
    {
        if(!job.getJobName().equals("") && job.getJobStatus().equals("Hiring")){
            String messageText = "New job posted: " + job.getJobName();
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", messageText);
            map.put("user", "employee");
            refEmployee.push().setValue(map);
        } else if (!job.getJobName().equals("New job posted: ") && job.getJobStatus().equals("Taken")) {
            String jobName = job.getJobName();
            String originalJobName = jobName.substring(0, jobName.indexOf("-"));
            String messageText = "Your job, " + originalJobName + ", was taken";
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", messageText);
            map.put("user", "employer");
            refEmployer.push().setValue(map);
        }

        return dbRef.child(job.getJobName()).setValue(job);
    }

    /**
     * This method is for used by employers to update their job that already exists in Firebase and by employees to take the job
     * @param jobName The Job Title (Edited by user)
     * @param jobDesc Job Description
     * @param jobStatus Job Status (Hiring?)
     * @param jobOwner Original Posted
     * @param jobOriginalName Used to update the correct firebase entity
     * @param jobEmployee Used to update the employee
     * @return
     */
    public Task<Void> UpdateJob(String jobName, String jobDesc, String jobStatus, String jobOwner, String jobOriginalName, String jobEmployee, String payment)
    {
        Map<String, Object> updateObject = new HashMap<>();
        updateObject.put("jobName", jobName);
        if(!jobDesc.equals(""))
        {
            updateObject.put("jobDescription", jobDesc);
        }
        updateObject.put("jobOwner", jobOwner);
        updateObject.put("jobStatus", jobStatus);
        updateObject.put("jobEmployee", jobEmployee);
        if(!payment.equals(""))
        {
            updateObject.put("jobPaymentAmount", payment);
        }
        return dbRef.child(jobOriginalName).updateChildren(updateObject);
    }

    /**
     * This a delete method used by Employers
     * @param jobOriginalName Used to delete the correct firebase entity
     * @return
     */
    public Task<Void> DeleteJob(String jobOriginalName)
    {
        return dbRef.child(jobOriginalName).removeValue();
    }

    /**
     * This method is used to query all jobs that are currently hiring (Used by employees)
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryAllAvailableJobs(String activeUser, EmployeeJobsActivity context)
    {
        ArrayList<Job> jobs = new ArrayList<>();
        Query jobByOwner = dbRef.orderByChild("Job");
        jobByOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    jobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobStatus().equals("Hiring"))
                        {
                            jobs.add(job);
                        }
                    }
                }
                Intent jobsIntent = new Intent(context, EmployeeJobsList.class);
                jobsIntent.putExtra("activeUser", activeUser);
                jobsIntent.putExtra("activeUserJobs", (Serializable) jobs);
                context.startActivity(jobsIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * This method is used to query all jobs that are currently hiring (Used by employees)
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryAllAvailableJobsMap(String activeUser, EmployeeJobsActivity context)
    {
        ArrayList<Job> jobs = new ArrayList<>();
        Query jobByOwner = dbRef.orderByChild("Job");
        jobByOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    jobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobStatus().equals("Hiring"))
                        {
                            jobs.add(job);
                        }
                    }
                }
                Intent jobsIntent = new Intent(context, EmployeeJobsListMap.class);
                jobsIntent.putExtra("activeUser", activeUser);
                jobsIntent.putExtra("activeUserJobs", (Serializable) jobs);
                context.startActivity(jobsIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This method is used to query all jobs for an employee (Used by employees for management)
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryAllEmployeesJobs(String activeUser, Context context)
    {
        ArrayList<Job> jobs = new ArrayList<>();
        Query jobByEmployee = dbRef.orderByChild("jobEmployee");
        jobByEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    jobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobEmployee().contains(activeUser))
                        {
                            jobs.add(job);
                        }
                    }
                }
                Intent jobsIntent = new Intent(context, EmployeeTakenJobsList.class);
                jobsIntent.putExtra("activeUser", activeUser);
                jobsIntent.putExtra("activeUserJobs", (Serializable) jobs);
                jobsIntent.putExtra("activeStatus", "view");
                context.startActivity(jobsIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This method is used to query all jobs for an employee (Used by employees for management)
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryAllEmployeesJobsMap(String activeUser, Context context)
    {
        ArrayList<Job> jobs = new ArrayList<>();
        Query jobByEmployee = dbRef.orderByChild("jobEmployee");
        jobByEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    jobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobEmployee().contains(activeUser))
                        {
                            jobs.add(job);
                        }
                    }
                }
                Intent jobsIntent = new Intent(context, EmployeeJobsListMap.class);
                jobsIntent.putExtra("activeUser", activeUser);
                jobsIntent.putExtra("activeUserJobs", (Serializable) jobs);
                jobsIntent.putExtra("activeStatus", "view");
                context.startActivity(jobsIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * This method is used to query all hiring jobs for an employer (Used by employees for updating/management)
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryEmployersPostedJobs(String activeUser, EmployerJobsActivity context)
    {
        ArrayList<Job> employerJobs = new ArrayList<>();
        Query jobByOwner = dbRef.orderByChild("jobOwner").equalTo(activeUser);
        jobByOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    employerJobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobStatus().equals("Hiring"))
                        {
                            employerJobs.add(job);
                        }
                    }
                }
                Intent employerPosted = new Intent(context, EmployerPosted.class);
                employerPosted.putExtra("activeUser", activeUser);
                employerPosted.putExtra("activeUserJobs", (Serializable) employerJobs);
                context.startActivity(employerPosted);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * This method is used to query all taken jobs for an employer
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryEmployersTakenJobs(String activeUser, EmployerJobsActivity context)
    {
        ArrayList<Job> employerJobs = new ArrayList<>();
        Query jobByOwner = dbRef.orderByChild("jobOwner").equalTo(activeUser);
        jobByOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    employerJobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobStatus().equals("Taken"))
                        {
                            employerJobs.add(job);
                        }
                    }
                }
                Intent employerPosted = new Intent(context, EmployerPosted.class);
                employerPosted.putExtra("activeUser", activeUser);
                employerPosted.putExtra("activeUserJobs", (Serializable) employerJobs);
                context.startActivity(employerPosted);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * This method is used to query all completed jobs for an employer
     * @param activeUser the current user logged into the application
     * @param context the original context of where they interact from
     */
    public void queryEmployersCompletedJobs(String activeUser, EmployerJobsActivity context)
    {
        ArrayList<Job> employerJobs = new ArrayList<>();
        Query jobByOwner = dbRef.orderByChild("jobOwner").equalTo(activeUser);
        jobByOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    employerJobs.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        Job job = snapshot2.getValue(Job.class);
                        if(job.getJobStatus().contains("Complete"))
                        {
                            employerJobs.add(job);
                        }
                    }
                }
                Intent employerPosted = new Intent(context, EmployerCompleted.class);
                employerPosted.putExtra("activeUser", activeUser);
                employerPosted.putExtra("activeUserJobs", (Serializable) employerJobs);
                context.startActivity(employerPosted);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


