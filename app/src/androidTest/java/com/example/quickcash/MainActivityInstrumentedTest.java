package com.example.quickcash;

import android.content.Context;
import android.util.Log;


import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    /*** User Home Page visibility check **/
    @Test
    public void checkIfUserHomePageIsVisible() {
        onView(withId(R.id.logout_icon));
    }

    /*** Login Page(Init page) visibility check **/
    @Test
    public void checkIfLoginPageIsVisible() {
        onView(withId(R.id.tvLoginPassword)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.btnLogin));
        onView(withId(R.id.loginRegisterBtn));
    }

    /**
     * test to check if we are not logged in when we dont put a valid user
     */
    @Test
    public void checkIfInvalidUserNotLoggedIn() {
        onView(withId(R.id.tvLoginEmail)).perform(typeText("ieieo@hotmail.com"));
        onView(withId(R.id.tvLoginPassword)).perform(typeText("eeeeee"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnLogin)).perform(click());
    }
//    /**
//     * Test to see when existing user presses login that they are redirected to dashboard
//     */
//    @Test
//    public void checkIfMoved2WelcomePage() {
//        onView(withId(R.id.tvLoginEmail)).perform(typeText("test2@hotmail.com"));
//        onView(withId(R.id.tvLoginPassword)).perform(typeText("abc12345!"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.btnLogin)).perform(click());
//        intended(hasComponent(EmployeeMainActivity.class.getName()));
//    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
}