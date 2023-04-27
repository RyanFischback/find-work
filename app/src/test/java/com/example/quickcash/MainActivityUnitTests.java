package com.example.quickcash;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityUnitTests
{
    static MainActivity mainActivity;

    @BeforeClass
    public static void setup() {

        mainActivity = new MainActivity();
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
//    public void loginButton_isPresent()
//    {
//
//    }
//    @Test
//    public void createNewAccount_isPresent()
//    {
//
//    }
}