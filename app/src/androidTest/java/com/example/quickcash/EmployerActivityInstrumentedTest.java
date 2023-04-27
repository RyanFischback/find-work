package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.dao.DAOJobs;
import com.example.quickcash.employees.EmployeeAccountActivity;
import com.example.quickcash.employees.EmployeeJobsActivity;
import com.example.quickcash.employees.EmployeeMainActivity;
import com.example.quickcash.employers.EmployerAccountActivity;
import com.example.quickcash.employers.EmployerAddJobBox;
import com.example.quickcash.employers.EmployerJobsActivity;
import com.example.quickcash.employers.EmployerMainActivity;
import com.example.quickcash.employers.EmployerNotificationActivity;
import com.example.quickcash.employers.EmployerPosted;

import org.junit.After;
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
public class EmployerActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<EmployerMainActivity> myRule = new ActivityScenarioRule<>(EmployerMainActivity.class);
    public IntentsTestRule<EmployerMainActivity> myIntentRule = new IntentsTestRule<>(EmployerMainActivity.class);

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
    public void checkIfEmployerNavigationBarIsVisible() {
        onView(withId(R.id.bottom_navigation));
    }

    /*** Employer Nav Bar Settings Option Redirect Test **/
    @Test
    public void checkIfSettingsRedirects() {
        Intents.init();
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployerAccountActivity.class.getName()));
        Intents.release();
    }

    /*** Employer Nav Bar Jobs Option Redirect Test **/
    @Test
    public void checkIfJobsRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        Intents.release();
    }

    /*** Employer Nav Bar Home Option Redirect Test **/
    @Test
    public void checkIfHomeRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        onView(withId(R.id.ic_home)).perform(click());
        intended(hasComponent(EmployerMainActivity.class.getName()));
        Intents.release();
    }

    /*** Employer Create new Job Test **/
    @Test
    public void checkIfPostNewJobFunctionWorking() throws InterruptedException {
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        onView(withId(R.id.addJob)).perform(click());
        onView(withText("Post a New Job in your Area")).check(matches(isDisplayed())); // verify dialog appears
        onView(withId(R.id.newJobName)).perform(typeText("Espresso Job Test"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.newJobDesc)).perform(typeText("Espresso Job Description"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.newJobPaymentAmount)).perform(typeText("100"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click()); // dialog positive button OK
        Thread.sleep(5000);
        intended(hasComponent(EmployerPosted.class.getName())); // redirected to view job
        Intents.release();
    }
    /*** Employer Posted Job Button Click Test **/
    @Test
    public void checkIfPostedJobsButtonRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        onView(withId(R.id.employer_jobs)).perform(click());
        Intents.release();
    }
    /*** Employer Taken Job Button Click Test **/
    @Test
    public void checkIfTakenJobsButtonRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        onView(withId(R.id.employer_jobs_taken)).perform(click());
        Intents.release();
    }
    /*** Employer Completed Job Button Click Test **/
    @Test
    public void checkIfCompletedJobsButtonRedirects() {
        Intents.init();
        onView(withId(R.id.ic_jobs)).perform(click());
        intended(hasComponent(EmployerJobsActivity.class.getName()));
        onView(withId(R.id.employer_jobs_completed)).perform(click());
        Intents.release();
    }
    /*** Employer Edit Profile Button Click Test **/
    @Test
    public void checkIfEditProfileDialogOpens() {
        Intents.init();
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployerAccountActivity.class.getName()));
        onView(withId(R.id.employer_edit_profile)).perform(click());
        Intents.release();
    }
    /*** Employer Notification Load Test **/
    @Test
    public void checkIfNotificationOpens() {
        Intents.init();
        onView(withId(R.id.btnNotifications)).perform(click());
        intended(hasComponent(EmployerNotificationActivity.class.getName()));
        Intents.release();
    }
    /*** Employer Nav Bar Settings Option Redirect Test & Validate Location Button Launches Map Activity **/
    @Test
    public void checkIfLocationButtonOpensMapActivity() {
        Intents.init();
        onView(withId(R.id.ic_setting)).perform(click());
        intended(hasComponent(EmployerAccountActivity.class.getName()));
        onView(withId(R.id.employer_edit_location)).perform(click());
        intended(hasComponent(MapsActivity.class.getName()));
    }

    /*** Employer Log out Test & Validate Main Activity **/
    @Test
    public void checkIfLogoutButtonClickRedirects() {
        Intents.init();
        onView(withId(R.id.btnLogout)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
        System.gc();
    }
}