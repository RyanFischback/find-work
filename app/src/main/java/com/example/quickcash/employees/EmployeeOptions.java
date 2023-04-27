package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.employers.EmployerMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmployeeOptions extends AppCompatActivity {

    private String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_option);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
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
}
