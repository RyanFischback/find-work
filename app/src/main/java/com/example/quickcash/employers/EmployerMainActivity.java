package com.example.quickcash.employers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.example.quickcash.employees.EmployeeMainActivity;
import com.example.quickcash.employees.EmployeeNotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


/**
 * This class is the main activity for employer where they will be presented with a dashboard and initialize all components
 */
public class EmployerMainActivity extends AppCompatActivity {

    private AppBarConfiguration AppBarConfiguration;
    private String activeUser;

    /**
     * onCreate set the layout to employer main and initialize the toolbar, drawer layout, and navigiation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_main);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.ic_home);

        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
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

                    case R.id.ic_home: return true;

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

    /**
     * Log's out user, Redirects to Login Page
     * @param view
     */
    public void changeViewToLogin(View view)
    {
        Intent intent = new Intent(EmployerMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Redirects to notification page
     * @param view
     */
    public void changeViewToNotifications(View view)
    {
        Intent intent = new Intent(EmployerMainActivity.this, EmployerNotificationActivity.class);
        startActivity(intent);
    }
}
