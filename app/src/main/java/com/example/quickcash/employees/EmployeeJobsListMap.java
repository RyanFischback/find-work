package com.example.quickcash.employees;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quickcash.Job;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EmployeeJobsListMap extends AppCompatActivity implements EmployeeSignUpJobBox.Listener, OnMapReadyCallback {

    private String activeUser;
    private ArrayList<Job> activeUserJobs;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_posted_map);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        activeUserJobs = (ArrayList<Job>) getIntent().getSerializableExtra("activeUserJobs");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_job);

        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);

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
        daoJobs.UpdateJob(name, description, "Taken", owner, name, activeUsername, "");
        viewMyJobs();
    }
    public void viewMyJobs()
    {
        DAOJobs daoJobs = new DAOJobs();
        daoJobs.queryAllEmployeesJobs(activeUser, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation(client); // zoom to current location
            createJobMarkers(); // create markers from Job (Firebase)

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // Triggered when user click any marker on the map
                    for (Job job : activeUserJobs)
                    {
                        if(marker.getTitle().contains(job.getJobName()) && job.getJobStatus().contains("Hiring"))
                        {
                            EmployeeSignUpJobBox employeeSignUpJobBox = new EmployeeSignUpJobBox();
                            Bundle jobParams = new Bundle();
                            jobParams.putString("jobName",  job.getJobName());
                            jobParams.putString("jobDesc",  job.getJobDescription());
                            jobParams.putString("jobStatus",  job.getJobStatus());
                            jobParams.putString("jobOwner",  job.getJobOwner());
                            jobParams.putString("jobPay",  job.getJobPaymentAmount());
                            employeeSignUpJobBox.setArguments(jobParams);
                            employeeSignUpJobBox.show(getSupportFragmentManager(),"Are you sure you want to take this job?");
                            break;
                        }
                        else
                        {
                            marker.showInfoWindow();
                        }
                    }
                    return true;
                }
            });
        }
        else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
    }
    private void getCurrentLocation(FusedLocationProviderClient client)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(location -> {
                if(location != null) {
                    mapFragment.getMapAsync(googleMap -> {
                        LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("You are here");
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
                        googleMap.addMarker(markerOptions).showInfoWindow();
                    });
                }
            });
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
    }
    private void createJobMarkers()
    {
        for(Job job : activeUserJobs)
        {
            //create map, marker foreach job
            Double latitude = Double.parseDouble(job.getJobLatitude());
            Double longitude = Double.parseDouble(job.getJobLongitude());

            LatLng jobPosition = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(jobPosition).title("JobName: " + job.getJobName()).snippet("Pay: " + job.getJobPaymentAmount() + " Status: " + job.getJobStatus())).showInfoWindow();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
    }

}
