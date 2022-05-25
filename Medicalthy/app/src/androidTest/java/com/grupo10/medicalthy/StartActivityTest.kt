package com.grupo10.medicalthy

import android.content.Intent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StartActivityTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<StartActivity> = ActivityTestRule(StartActivity::class.java, true, false)

    //ActivityScenarioRule(StartActivity::class.java)

    //var ActivityTestRule : ActivityTestRule<StartActivity> = ActivityTestRule

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testStartupCheckLayoutVisibility(){
        onView(withId(R.id.startLayout)).check(matches(withEffectiveVisibility(Visibility.VISIBLE))) //check layout se ha iniciado correctamente

    }

    @Test
    fun testSignInButtonCheckChangeActivity(){
        onView(withId(R.id.goSignIn)).perform(click())
        //checks del layout
        onView(withId(R.id.signInEmail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.signInPassword)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.googleButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSignUpButtonCheckChangeActivity(){
        onView(withId(R.id.goSignUp)).perform(click())
        //checks del layout
        onView(withId(R.id.editTextName)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.editTextSurname)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.editTextAge)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.signUpEmail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.signUpPassword)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.signUpButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}