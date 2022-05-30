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
class ShowMedicineActivityTest {
    @Rule
    @JvmField
    var mActivityrule: ActivityTestRule<SignInActivity> = ActivityTestRule(SignInActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    // Test caso sin medicinas en el plan
    @Test
    fun testWithoutMedicines() {
        val email = "test_no_med@gmail.com"
        val password = "test_no_med"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
    }

    // Test caso con medicinas en el plan
    @Test
    fun testWithMedicines() {
        val email = "klk123@gmail.com"
        val password = "klk123"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).check(matches(isDisplayed()))
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
    }

    // Test caso con medicinas en el plan y click en la medicina
    @Test
    fun testWithMedicinesClickMedicine() {
        val email = "klk123@gmail.com"
        val password = "klk123"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        Espresso.onView(withText("Esto corresponde a la descripción del Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
    }

    // Test caso sin medicinas en el plan y click largo para acceder
    @Test
    fun testWithoutMedicinesLongClick() {
        val email = "test_no_med@gmail.com"
        val password = "test_no_med"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.longClick())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
    }

    // Test caso con medicinas en el plan y click largo para acceder
    @Test
    fun testWithMedicinesLongClick() {
        val email = "klk123@gmail.com"
        val password = "klk123"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.longClick())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).perform(ViewActions.longClick())
        Espresso.onView(withText("Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        Espresso.onView(withText("Esto corresponde a la descripción del Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
    }

    // Test caso con medicinas en el plan, entrar dos veces a la medicina
    @Test
    fun testWithMedicinesGoBack(){
        val email = "klk123@gmail.com"
        val password = "klk123"
        Espresso.onView(ViewMatchers.withId(R.id.signInEmail)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.signInPassword)).perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        Espresso.onView(withText("Esto corresponde a la descripción del Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.btnAddMedicine)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnPharmacy)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminPerson)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnMedicationHistory)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnShoppingCart)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnShowShots)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Tu medicación")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).check(matches(isDisplayed()))
        Espresso.onView(withText("Ibuprofeno Kern Pharma -> 13:24")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withText("Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        Espresso.onView(withText("Esto corresponde a la descripción del Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
    }
}