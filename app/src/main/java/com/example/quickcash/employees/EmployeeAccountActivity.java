package com.example.quickcash.employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.MainActivity;
import com.example.quickcash.MapsActivity;
import com.example.quickcash.R;
import com.example.quickcash.User;
import com.example.quickcash.dao.DAOJobs;
import com.example.quickcash.dao.DAOUser;
import com.example.quickcash.employers.EmployerAccountActivity;
import com.example.quickcash.employers.EmployerEditJobBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.quickcash.R;

import java.util.ArrayList;

/**
 * Class used for employee account settings activity
 * Edit employee profile, resume, contact info, device settings, etc
 */
public class EmployeeAccountActivity extends AppCompatActivity {
    private String activeUser;

    /**
     * onCreate set the view to employee_account
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_account);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        TextView userEmail = (TextView) findViewById(R.id.user_email);
        userEmail.setText(activeUser);
        Button mapButton = (Button) findViewById(R.id.employee_edit_location);
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeViewToMaps(v);
            }
        });
        Button editProfileButton = (Button) findViewById(R.id.employee_edit_profile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editProfile();
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.ic_setting);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

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

                    case R.id.ic_setting:
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * This method finds user and starts activity to edit details
     */
    public void editProfile()
    {
        DAOUser daoUser = new DAOUser();
        daoUser.FindUserByEmail(activeUser, this);
    }

    /**
     * Redirects user to the Map view
     * @param view
     */
    public void changeViewToMaps(View view)
    {
        Intent intent = new Intent(EmployeeAccountActivity.this, MapsActivity.class);
        startActivity(intent);
    }


}
