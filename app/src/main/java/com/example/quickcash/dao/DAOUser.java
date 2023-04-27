package com.example.quickcash.dao;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quickcash.MainActivity;
import com.example.quickcash.User;
import com.example.quickcash.employees.EmployeeEditProfile;
import com.example.quickcash.employees.EmployeeMainActivity;
import com.example.quickcash.employers.EmployerEditProfile;
import com.example.quickcash.employers.EmployerMainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for any database functions
 * Author: Ryan Fischback
 */
public class DAOUser
{
    private DatabaseReference dbRef;

    public DAOUser()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(User.class.getSimpleName());
    }

    /**
     * Creates user in db
     * @param user
     * @return
     */
    public Task<Void> create(User user)
    {
        String userEmail = user.getEmail().replace("." , "");
        return dbRef.child(userEmail).setValue(user);
    }

    /**
     * Querying for a specific user, Called from MainActivity
     * @param email
     * @param password
     * @param main
     */
    public void queryUserByEmail(String email, String password, MainActivity main)
    {
        Query userQuery = dbRef.orderByChild("email").equalTo(email);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        User user = snapshot2.getValue(User.class);
                        if(user.getPassword().equals(password))
                        {
                            Toast.makeText(main.getBaseContext(), "Welcome back: " + email + "!", Toast.LENGTH_LONG).show();
                            switch(user.getUserType())
                            {
                                case "Employee":
                                    Intent employee = new Intent(main, EmployeeMainActivity.class);
                                    employee.putExtra("activeUser", email);
                                    main.startActivity(employee);
                                    break;
                                case "Employer":
                                    Intent employer = new Intent(main, EmployerMainActivity.class);
                                    employer.putExtra("activeUser", email);
                                    main.startActivity(employer);
                                    break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
    public Task<Void> UpdateUser(String username, String password, String originalUsername)
    {
        Map<String, Object> updateObject = new HashMap<>();
        updateObject.put("email", username);
        updateObject.put("password", password);
        updateObject.put("userType", "Employee");
        originalUsername = originalUsername.replace(".", "");
        username = username.replace(".", "");
        if(!originalUsername.equals(username)) // if username changed we have to remove parent node and recreate.
        {
            dbRef.child(originalUsername).removeValue();
        }
        return dbRef.child(username).setValue(updateObject);
    }
    public void FindUserByEmail(String username, Context context)
    {
        ArrayList<User> users = new ArrayList<>();
        Query userQuery = dbRef.orderByChild("email").equalTo(username);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    users.clear();
                    for(DataSnapshot snapshot2 : snapshot.getChildren())
                    {
                        User user = snapshot2.getValue(User.class);
                        users.add(user);
                    }
                    Intent intent;
                    if(context.toString().contains("Employer"))
                    {
                        intent = new Intent(context, EmployerEditProfile.class);
                    }
                    else
                    {
                        intent = new Intent(context, EmployeeEditProfile.class);
                    }
                    intent.putExtra("activeUser", username);
                    intent.putExtra("activeUserList", (Serializable) users);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}

