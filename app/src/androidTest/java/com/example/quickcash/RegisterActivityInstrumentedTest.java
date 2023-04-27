package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.quickcash.employers.EmployerMainActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<RegisterActivity> myRule = new ActivityScenarioRule<>(RegisterActivity.class);
    public IntentsTestRule<RegisterActivity> myIntentRule = new IntentsTestRule<>(RegisterActivity.class);

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

    /*** Register Page visibility check **/
    @Test
    public void checkIfRegistrationPageIsVisible() {
        onView(withId(R.id.registerEmailField)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.registerPasswordField)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.registerConfirmPasswordField)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.registerBtn));
        onView(withId(R.id.btnRedirectLogin));
    }
    /***
     * Test to validate employee types
     */
    @Test
    public void checkIfEmployeeTypesArePresent()
    {
        onView(withId(R.id.employee_employer)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.employee_employer)).check(matches(withSpinnerText(containsString("Employee"))));
        onView(withId(R.id.employee_employer)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employer"))).perform(click());
        onView(withId(R.id.employee_employer)).check(matches(withSpinnerText(containsString("Employer"))));
    }
    /***
     * Test to test validation on registering without enough info
     */
    @Test
    public void checkErrorIfRegisteringWithoutInformation()
    {
        onView(withId(R.id.registerEmailField)).perform(typeText("bob"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(click());
        onView(withId(R.id.registerBtn)).check(matches(isDisplayed())); // if its still displayed then we didnt register
        onView(withId(R.id.registerPasswordField)).perform(typeText("bob"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).check(matches(isDisplayed())); // if its still displayed then we didnt register
        onView(withId(R.id.registerConfirmPasswordField)).perform(typeText("bob2"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(click());
        onView(withId(R.id.registerBtn)).check(matches(isDisplayed())); // if its still displayed then we didnt register
    }

    /***
     * Testing registering a valid user
     */
    @Test
    public void checkIfRegisterSuccess()
    {
        onView(withId(R.id.registerEmailField)).perform(typeText("abc123@hotmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordField)).perform(typeText("bananas123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerConfirmPasswordField)).perform(typeText("bananas123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.employee_employer)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employer"))).perform(click());
        onView(withId(R.id.registerBtn)).perform(click());
        intended(hasComponent(EmployerMainActivity.class.getName()));
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
}