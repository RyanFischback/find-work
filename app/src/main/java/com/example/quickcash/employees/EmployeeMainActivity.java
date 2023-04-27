package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.employers.EmployerAccountActivity;
import com.example.quickcash.employers.EmployerJobsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

/**
 * Class used for the main activities an employee sees upon logging in
 */
public class EmployeeMainActivity extends AppCompatActivity {

    private String activeUser;

    /**
     * onCreate we set the view to employee main and initialize the toolbar, drawer layout, and navigation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);
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

                    case R.id.ic_home: return true;

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

    /**
     * Log's out user, Redirects to Login Page
     * @param view
     */
    public void changeViewToLogin(View view)
    {
        Intent intent = new Intent(EmployeeMainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Redirects to notification page
     * @param view
     */
    public void changeViewToNotifications(View view)
    {
        Intent intent = new Intent(EmployeeMainActivity.this, EmployeeNotificationActivity.class);
        startActivity(intent);
    }
}
