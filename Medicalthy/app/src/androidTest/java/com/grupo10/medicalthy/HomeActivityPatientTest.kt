package com.grupo10.medicalthy

import org.junit.Assert.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class HomeActivityPatientTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<HomeActivityPatient> = ActivityTestRule(HomeActivityPatient::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testShowShotsPatientCheckChangeActivity(){
        onView(withId(R.id.btnShowShotsPatient)).check(matches(isDisplayed())).check(matches(
            isClickable()))
        onView(withId(R.id.btnShowShotsPatient)).perform(click())
        onView(withText("Tu medicación")).check(matches(isDisplayed()))

    }
}