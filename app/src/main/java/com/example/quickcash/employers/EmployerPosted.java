package com.example.quickcash.employers;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * This class is the activity for the job management page used by Employers to interact with their jobs
 */
public class EmployerPosted extends AppCompatActivity implements EmployerEditJobBox.Listener {

    private String activeUser;
    private String jobOriginalName;
    private ArrayList<Job> activeUserJobs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_posted);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        if(activeUserJobs == null)
        {
            activeUserJobs = (ArrayList<Job>) getIntent().getSerializableExtra("activeUserJobs");
        }
        ListView activeUserJobsView = findViewById(R.id.activeUserJobs);
        ArrayAdapter<Job> arrayAdapter = new JobAdapter(
                this,
                activeUserJobs);
        activeUserJobsView.setAdapter(arrayAdapter);
        activeUserJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job job = activeUserJobs.get(position);
                jobOriginalName = job.getJobName();
                EmployerEditJobBox employerEditJobBox = new EmployerEditJobBox();
                Bundle jobParams = new Bundle();
                jobParams.putString("jobName",  job.getJobName());
                jobParams.putString("jobDesc",  job.getJobDescription());
                jobParams.putString("jobPay",  job.getJobPaymentAmount());
                jobParams.putString("jobOriginalName", jobOriginalName);
                employerEditJobBox.setArguments(jobParams);
                employerEditJobBox.show(getSupportFragmentManager(),"Edit Job");
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_setting:
                        Intent setting = new Intent(getApplicationContext(), EmployerAccountActivity.class);
                        setting.putExtra("activeUser", activeUser);
                        startActivity(setting);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_home:
                        Intent home = new Intent(getApplicationContext(), EmployerMainActivity.class);
                        home.putExtra("activeUser", activeUser);
                        startActivity(home);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_jobs:
                        Intent jobs = new Intent(getApplicationContext(), EmployerJobsActivity.class);
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
    public void getInfo(String name, String description, String jobOriginalName, String payment, int activity) {
        DAOJobs daoJobs = new DAOJobs();
        if(activity == 0) //del
        {
            daoJobs.DeleteJob(jobOriginalName);
        }
        else if (activity == 1) //update
        {
            daoJobs.UpdateJob(name, description, "Hiring", activeUser, jobOriginalName, "", payment);
        }
    }
}
