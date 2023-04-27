package com.example.quickcash.employees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.quickcash.R;
import com.example.quickcash.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.example.quickcash.R;

public class EmployeeNotificationActivity extends AppCompatActivity {
    LinearLayout linear_layout;
    RelativeLayout relative_layout;
    ScrollView scrollView;
    private String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_notifications);

        linear_layout = findViewById(R.id.linear_layout);
        relative_layout = findViewById(R.id.relative_layout);
        scrollView = findViewById(R.id.scrollView);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference1 = db.getReferenceFromUrl("https://quick-cash-4d952-default-rtdb.firebaseio.com/messages/employee");

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map map = (Map) snapshot.getValue();
                String message = map.get("message").toString();
                String userType = map.get("user").toString();

                if(userType.equals("employee")){
                    addNotificationBox(message);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void addNotificationBox(String message){
        TextView textView = new TextView(EmployeeNotificationActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;
        lp2.gravity = Gravity.LEFT;

        textView.setLayoutParams(lp2);
        linear_layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_UP);
    }

}
