package com.example.quickcash;

import com.example.quickcash.employees.EmployeeMainActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserHomeActivityUnitTests
{
    static EmployeeMainActivity userHomeActivity;

    @BeforeClass
    public static void setup() {

        userHomeActivity = new EmployeeMainActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

//    @Test
//    public void logoutButton_isPresent()
//    {
//
//    }
}
