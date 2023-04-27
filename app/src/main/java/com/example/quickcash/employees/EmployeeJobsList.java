package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Job;
import com.example.quickcash.JobAdapter;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.example.quickcash.employers.EmployerEditJobBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EmployeeJobsList extends AppCompatActivity implements EmployeeSignUpJobBox.Listener {

    private String activeUser;
    private ArrayList<Job> activeUserJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_posted);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        activeUserJobs = (ArrayList<Job>) getIntent().getSerializableExtra("activeUserJobs");
        ListView activeUserJobsView = findViewById(R.id.activeUserJobs);
        ArrayAdapter<Job> arrayAdapter = new JobAdapter(
                this,
                activeUserJobs);
        activeUserJobsView.setAdapter(arrayAdapter);
        activeUserJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job job = activeUserJobs.get(position);
                EmployeeSignUpJobBox employeeSignUpJobBox = new EmployeeSignUpJobBox();
                Bundle jobParams = new Bundle();
                jobParams.putString("jobName",  job.getJobName());
                jobParams.putString("jobDesc",  job.getJobDescription());
                jobParams.putString("jobStatus",  job.getJobStatus());
                jobParams.putString("jobOwner",  job.getJobOwner());
                jobParams.putString("jobPay",  job.getJobPaymentAmount());
                employeeSignUpJobBox.setArguments(jobParams);
                employeeSignUpJobBox.show(getSupportFragmentManager(),"Are you sure you want to take this job?");
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_setting:
                        Intent setting = new Intent(getApplicationContext(), EmployeeAccountActivity.class);
                        setting.putExtra("activeUser", activeUser);
                        startActivity(setting);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_home:
                        Intent home = new Intent(getApplicationContext(), EmployeeMainActivity.class);
                        home.putExtra("activeUser", activeUser);
                        startActivity(home);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_jobs:
                        Intent jobs = new Intent(getApplicationContext(), EmployeeJobsActivity.class);
                        jobs.putExtra("activeUser", activeUser);
                        startActivity(jobs);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void getInfo(String name, String description, String owner) {
        String activeUsername = getIntent().getExtras().getString("activeUser");
        DAOJobs daoJobs = new DAOJobs();
        if(activeUsername == null)
        {
            activeUsername = "EspressoTest@test.com";
            activeUser = activeUsername;
        }
        daoJobs.UpdateJob(name, description, "Taken", owner, name, activeUsername, "");
        viewMyJobs();
    }
    public void viewMyJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryAllEmployeesJobs(activeUser, this);
    }
}
