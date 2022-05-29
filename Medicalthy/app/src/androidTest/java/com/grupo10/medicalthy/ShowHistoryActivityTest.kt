package com.grupo10.medicalthy

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matcher

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowHistoryActivityTest {

    @Rule
    @JvmField
    var mActivityrule: ActivityTestRule<SignInActivity> = ActivityTestRule(SignInActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testHistoryWithoutMedicines(){
        val email = "test_no_med@gmail.com"
        val password = "test_no_med"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.btnMedicationHistory)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.vertical_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testHistoryWithMedicines(){
        val email = "klk123@gmail.com"
        val password = "klk123"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.btnMedicationHistory)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(withText("Wed May 25 13:25:58 GMT 2022 -> true")).check(matches(isDisplayed()))
    }
}