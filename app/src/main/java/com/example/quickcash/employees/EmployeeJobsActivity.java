package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * Class used for employee jobs activities
 * Functions will include managing their jobs, adding new jobs
 */
public class EmployeeJobsActivity extends AppCompatActivity {

    private String activeUser;

    /**
     * onCreate set the layout to employee jobs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_jobs);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        Button findJob = (Button) findViewById(R.id.findNewJob);
        findJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAllJobs();
            }
        });
        Button findJobMaps = (Button) findViewById(R.id.findNewJobMap);
        findJobMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAllJobsMap();
            }
        });
        Button myJobs = (Button) findViewById(R.id.employee_jobs);
        myJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMyJobs();
            }
        });
        Button myJobsMap = (Button) findViewById(R.id.employee_jobsMaps);
        myJobsMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMyJobsMap();
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.ic_home);
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
                        return true;

                }
                return false;
            }
        });
    }
    public void viewAllJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryAllAvailableJobs(activeUser, this);
    }
    public void viewMyJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        if(activeUser == null)
        {
            activeUser = "EspressoTest@test.com";
        }
        daoJobs.queryAllEmployeesJobs(activeUser, this);
    }
    public void viewAllJobsMap()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryAllAvailableJobsMap(activeUser, this);
    }
    public void viewMyJobsMap()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryAllEmployeesJobsMap(activeUser, this);
    }
}
