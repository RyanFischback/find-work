package com.example.quickcash.employers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.UserEditBox;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.User;
import com.example.quickcash.UserAdapter;
import com.example.quickcash.dao.DAOUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EmployerEditProfile extends AppCompatActivity implements UserEditBox.Listener {
    private String activeUser;
    private ArrayList<User> activeUserList;
    /**
     * onCreate set the layout to account (will be updated)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_edit_profile);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        if(activeUserList == null)
        {
            activeUserList = (ArrayList<User>) getIntent().getSerializableExtra("activeUserList");
        }
        ListView activeUserListView = findViewById(R.id.activeUserList);
        ArrayAdapter<User> arrayAdapter = new UserAdapter(
                this,
                activeUserList);
        activeUserListView.setAdapter(arrayAdapter);
        activeUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = activeUserList.get(position);
                UserEditBox employerEditBox = new UserEditBox();
                Bundle userParams = new Bundle();
                userParams.putString("userName",  user.getEmail());
                userParams.putString("userPass",  user.getPassword());
                userParams.putString("userOriginalName", activeUser);
                employerEditBox.setArguments(userParams);
                employerEditBox.show(getSupportFragmentManager(),"Edit User");
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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
    @Override
    public void getInfo(String username, String password, String originalUsername) {
        DAOUser daoUser = new DAOUser();
        daoUser.UpdateUser(username, password, originalUsername);
        Toast.makeText(getBaseContext(), "Please Relog with your new information:" + "Username:" + username + "\n Password:" + password, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
