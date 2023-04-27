package com.example.quickcash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quickcash.dao.DAOUser;
import com.example.quickcash.employees.EmployeeMainActivity;
import com.example.quickcash.employers.EmployerMainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

/**
 * This class is used for our register activity where our users register an account
 */
public class RegisterActivity extends AppCompatActivity
{
    static String WELCOME_MESSAGE = "";

    /**
     * onCreate set the layout to registration main
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_main);
    }

    /**
     * Validates registration fields and Registers user if they pass all the validation checks
     * @param view
     */
    public void registerUser(View view)
    {

        EditText emailInput = (EditText)findViewById(R.id.registerEmailField);
        String email = emailInput.getText().toString().trim();
        boolean isEmailValid = validateEmail(email);
        if(!isEmailValid) // if we dont have a valid email
        {
            Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        EditText passwordInput = (EditText)findViewById(R.id.registerPasswordField);
        String password = passwordInput.getText().toString().trim();
        boolean isPasswordValid = validatePassword(password);
        if(!isPasswordValid)
        {
            Toast.makeText(getBaseContext(), "Please enter a password that is longer than 8 characters", Toast.LENGTH_SHORT).show();
        }
        EditText confirmPasswordInput = (EditText)findViewById(R.id.registerConfirmPasswordField);
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        boolean isConfirmPasswordMatching = confirmPassword.equals(password);
        if(!isConfirmPasswordMatching)
        {
            Toast.makeText(getBaseContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
        if(isEmailValid && isPasswordValid && isConfirmPasswordMatching)
        {
            Spinner userTypeSelect = (Spinner) findViewById(R.id.employee_employer);
            String userType = userTypeSelect.getSelectedItem().toString().trim();
            ArrayList<String> latLng = getCurrentLocation();
            String longitude = latLng.get(0);
            String latitude = latLng.get(1);
            User emp = new User(email, password, userType, latitude, longitude);
            DAOUser daoUser = new DAOUser();
            daoUser.create(emp);
            WELCOME_MESSAGE = "Welcome: " + "'" + email + "'" + "You registered as an " + "'" + userType + "'" + " Enjoy!";
            switch(userType)
            {
                case "Employee":
                    Intent intentEmployee = new Intent(RegisterActivity.this, EmployeeMainActivity.class);
                    intentEmployee.putExtra("activeUser", email);
                    startActivity(intentEmployee);
                    break;
                case "Employer":
                    Intent intentEmployer = new Intent(RegisterActivity.this, EmployerMainActivity.class);
                    intentEmployer.putExtra("activeUser", email);
                    startActivity(intentEmployer);
                    break;
            }
        }
    }

    private ArrayList<String> getCurrentLocation()
    {
        ArrayList<String> latLng = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
        else
        {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latLng.add(String.valueOf(location.getLongitude()));
            latLng.add(String.valueOf(location.getLatitude()));
        }

        return latLng;
    }

    /**
     * Validates email using regex
     * @param email text from email field
     * @return true if valid, false if not
     */
    public boolean validateEmail(String email)
    {
        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailRegex);
    }

    /**
     * Validates password field
     * @param password password from user
     * @return true if password length >= 8, false if not
     */
    public boolean validatePassword(String password)
    {
        return password.length() >= 8;
    }

    /**
     * Redirects user to login page
     * @param view
     */
    public void changeViewToLogin(View view)
    {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
