package com.example.quickcash.employers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.MapsActivity;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmployerAccountActivity extends AppCompatActivity {

    private String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_account);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.ic_home);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        TextView userEmail = (TextView) findViewById(R.id.user_email);
        userEmail.setText("Welcome: " + activeUser);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_setting:
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
        Button editProfileButton = (Button) findViewById(R.id.employer_edit_profile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editProfile();
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
        Intent intent = new Intent(EmployerAccountActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}

