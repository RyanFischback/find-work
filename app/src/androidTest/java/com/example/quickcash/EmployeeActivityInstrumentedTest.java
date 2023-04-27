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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.core.internal.deps.guava.collect.Maps;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.employees.EmployeeAccountActivity;
import com.example.quickcash.employees.EmployeeJobsActivity;
import com.example.quickcash.employees.EmployeeJobsList;
import com.example.quickcash.employees.EmployeeMainActivity;
import com.example.quickcash.employees.EmployeeNotificationActivity;
import com.example.quickcash.employees.EmployeeSignUpJobBox;
import com.example.quickcash.employees.EmployeeTakenJobsList;
import com.example.quickcash.employers.EmployerAccountActivity;
import com.example.quickcash.employers.EmployerJobsActivity;
import com.example.quickcash.employers.EmployerPosted;

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
public class EmployeeActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<EmployeeMainActivity> myRule = new ActivityScenarioRule<>(EmployeeMainActivity.class);
    public IntentsTestRule<EmployeeMainActivity> myIntentRule = new IntentsTestRule<>(EmployeeMainActivity.class);

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

    /*** Employee Nav Bar visibility check **/
    @Test
    public void checkIfEmployeeNavigationBarIsVisible() {
        onView(withId(R.id.bottom_navigation));
    }

    /*** Employee Nav Bar Settings Option Redirect Test **/
    @Test
    public void checkIfSettingsRedirects() {
        Intents.init();
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployeeAccountActivity.class.getName()));
        Intents.release();
    }

    /*** Employee Nav Bar Jobs Option Redirect Test **/
    @Test
    public void checkIfJobsRedirects() {
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployeeJobsActivity.class.getName()));
        Intents.release();
    }

    /*** Employee Nav Bar Home Option Redirect Test **/
    @Test
    public void checkIfHomeRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployeeJobsActivity.class.getName()));
        onView(withId(R.id.ic_home)).perform(click());
        intended(hasComponent(EmployeeMainActivity.class.getName()));
    }


    /*** Employee Nav Bar Settings Option Redirect Test & Validate Location Button Launches Map Activity **/
    @Test
    public void checkIfLocationButtonOpensMapActivity() {
        Intents.init();
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployeeAccountActivity.class.getName()));
        onView(withId(R.id.employee_edit_location)).perform(click());
        intended(hasComponent(MapsActivity.class.getName()));

    }

    /*** Employee Log out Test & Validate Main Activity **/
    @Test
    public void checkIfLogoutButtonClickRedirects() {
        Intents.init();
        onView(withId(R.id.btnLogout)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    /*** Employee notification button test **/
    @Test
    public void checkIfNotificationButtonClickRedirects() {
        Intents.init();
        onView(withId(R.id.btnNotifications)).perform(click());
        intended(hasComponent(EmployeeNotificationActivity.class.getName()));
        Intents.release();
    }

    /*** Employee Find new Job Test **/
    @Test
    public void checkIfFindNewJobFunctionWorks() throws InterruptedException {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployeeJobsActivity.class.getName()));
        onView(withId(R.id.findNewJob)).perform(click());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.activeUserJobs)).atPosition(0).perform(click()); //click first list item (job)
        onView(withText("Are you sure you want to take this job?")).check(matches(isDisplayed())); // verify dialog appears
        onView(withId(android.R.id.button1)).perform(click()); // dialog positive button OK
        Intents.release();
    }
    /*** Employee Taken Jobs Test **/
    @Test
    public void checkIfTakenJobsLoads() throws InterruptedException {
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployeeJobsActivity.class.getName()));
        onView(withId(R.id.employee_jobs)).perform(click());
        Thread.sleep(5000);
        intended(hasComponent(EmployeeTakenJobsList.class.getName())); // redirected to view job
        Intents.release();
    }
    /*** Employee Edit Profile Button Click Test **/
    @Test
    public void checkIfEditProfileDialogOpens() {
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployeeAccountActivity.class.getName()));
        onView(withId(R.id.employee_edit_profile)).perform(click());
        Intents.release();
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
        System.gc();
    }
}