package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Job;
import com.example.quickcash.JobAdapter;
import com.example.quickcash.PayPalConfig;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EmployeeTakenJobsList extends AppCompatActivity implements EmployeeCompleteJobBox.Listener {

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
                if(!job.getJobStatus().contains("Complete"))
                {
                    EmployeeCompleteJobBox employeeCompleteJobBox = new EmployeeCompleteJobBox();
                    Bundle jobParams = new Bundle();
                    jobParams.putString("jobName",  job.getJobName());
                    jobParams.putString("jobDesc",  job.getJobDescription());
                    jobParams.putString("jobStatus",  job.getJobStatus());
                    jobParams.putString("jobOwner",  job.getJobOwner());
                    jobParams.putString("jobPay",  job.getJobPaymentAmount());
                    employeeCompleteJobBox.setArguments(jobParams);
                    employeeCompleteJobBox.show(getSupportFragmentManager(),"Complete this job?");
                }
                else if (!job.getJobStatus().contains("Paid"))
                {
                    Toast.makeText(getBaseContext(), "This Job Is Complete & Waiting for Employer to Pay", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "This Job Is Complete & Paid", Toast.LENGTH_LONG).show();
                }
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
    public void getInfo(String name, String owner, String payment) {
        DAOJobs daoJobs = new DAOJobs();
        if(activeUser == null)
        {
            activeUser = "EspressoTest@test.com";
        }
        daoJobs.UpdateJob(name, "", "Complete", owner, name, activeUser, payment);
        daoJobs.queryAllEmployeesJobs(activeUser, this);
    }
}
