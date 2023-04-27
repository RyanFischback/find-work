package com.example.quickcash;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class RegisterActivityUnitTests
{
    static RegisterActivity registerActivity;

    @BeforeClass
    public static void setup() {

        registerActivity = new RegisterActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

//    @Test
//    public void emailField_isPresent()
//    {
//
//    }
//    @Test
//    public void passwordField_isPresent()
//    {
//
//    }
//    @Test
//    public void confirmPasswordField_isPresent()
//    {
//
//    }
}
