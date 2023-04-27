package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.quickcash.dao.DAOUser;

/**
 * This class is our login activity where a user can log in
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate set the layout to activity main
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Grabs input from fields and queries for that user, Redirects to their appropriate homepage on success
     * @param view
     */
    public void loginUser(View view)
    {
        EditText emailInput = (EditText)findViewById(R.id.tvLoginEmail);
        EditText passwordInput = (EditText)findViewById(R.id.tvLoginPassword);
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if(!email.isEmpty())
        {
            DAOUser daoUser = new DAOUser();
            daoUser.queryUserByEmail(email, password, this);
        }
    }


    /**
     * Redirects user to the Register view
     * @param view
     */
    public void changeViewToRegister(View view)
    {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}