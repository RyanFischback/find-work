package com.example.quickcash.employers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quickcash.Job;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.example.quickcash.dao.DAOUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used for employer jobs activities
 * Functions will include adding jobs, viewing existing jobs posted, managing jobs
 */
public class EmployerJobsActivity extends AppCompatActivity implements EmployerAddJobBox.Listener {


    private String activeUser;
    /**
     * onCreate set the layout to employer jobs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_jobs);
        Button addJobs = (Button) findViewById(R.id.addJob);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               employerInputName();
            }
        });
        Button viewMyPostedJobs = (Button) findViewById(R.id.employer_jobs);
        viewMyPostedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPostedJobs();
            }
        });
        Button viewMyTakenJobs = (Button) findViewById(R.id.employer_jobs_taken);
        viewMyTakenJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTakenJobs();
            }
        });
        Button viewMyCompletedJobs = (Button) findViewById(R.id.employer_jobs_completed);
        viewMyCompletedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCompletedJobs();
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.ic_home);
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
                        return true;
                }
                return false;
            }
        });
    }

    public void employerInputName(){
        EmployerAddJobBox employerAddJobBox = new EmployerAddJobBox();
        employerAddJobBox.show(getSupportFragmentManager(),"Add Job");
    }

    public void viewPostedJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryEmployersPostedJobs(activeUser, this);
    }

    public void viewTakenJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryEmployersTakenJobs(activeUser, this);
    }

    public void viewCompletedJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryEmployersCompletedJobs(activeUser, this);
    }

    @Override
    public void getInfo(String name, String description, String payment){
        String activeUsername = getIntent().getExtras().getString("activeUser");
        ArrayList<String> latLng = getCurrentLocation();
        latLng = getRandomLocationFromCurrentLocation(latLng.get(0), latLng.get(1));
        if(activeUsername == null)
        {
            activeUsername = "EspressoTest@test.com";
        }
        Job job = new Job(name, description, "Hiring", activeUsername, "", payment, latLng.get(0), latLng.get(1));
        DAOJobs newJob = new DAOJobs();
        newJob.PostJob(job);
        newJob.queryEmployersPostedJobs(activeUsername, this);
    }

    private ArrayList<String> getCurrentLocation()
    {
        ArrayList<String> latLng = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latLng.add(String.valueOf(location.getLongitude()));
        latLng.add(String.valueOf(location.getLatitude()));
        return latLng;
    }

    //modified: https://stackoverflow.com/questions/36905396/randomly-generating-a-latlng-within-a-radius-yields-a-point-out-of-bounds
    private ArrayList<String> getRandomLocationFromCurrentLocation(String longitude, String latitude) {
        double x0 = Double.parseDouble(longitude);
        double y0 = Double.parseDouble(latitude);
        ArrayList<String> latLng = new ArrayList<>();

        Random random = new Random();

        // Convert radius from meters to degrees.
        double radiusInDegrees = 1000 / 111320f;

        // Get a random distance and a random angle.
        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        // Get the x and y delta values.
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Compensate the x value.
        double new_x = x / Math.cos(Math.toRadians(y0));

        double foundLatitude;
        double foundLongitude;

        foundLatitude = y0 + y;
        foundLongitude = x0 + new_x;

        latLng.add(String.valueOf(foundLatitude));
        latLng.add(String.valueOf(foundLongitude));
        return latLng;
    }

}
