package com.grupo10.medicalthy

import org.junit.Assert.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.PressBackAction
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

class AddPatientActivityTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<AddPatientActivity> = ActivityTestRule(AddPatientActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testAddPatientCheckChangeActivity(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        onView(withText("Introduzca la cuenta de la persona que quiere cuidar")).check(matches(
            isDisplayed()))
        onView(withId(R.id.signInEmailPatient)).check(matches(isDisplayed()))
        onView(withId(R.id.signInPasswordPatient)).check(matches(isDisplayed()))
        onView(withText("Añadir cuenta")).check(matches(isDisplayed())).check(matches(isClickable()))
    }

    @Test
    fun testAddPatientPressBackCheckCorrectDialogMessage(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        onView(withText("Volver a \"Añadir paciente\"")).check(matches(isDisplayed()))
        onView(withText("¿Seguro que quieres volver sin añadir un paciente?")).check(matches(
            isDisplayed()))
        onView(withText("Sí")).check(matches(isDisplayed())).check(matches(isClickable()))
        onView(withText("No")).check(matches(isDisplayed())).check(matches(isClickable()))
    }

    @Test
    fun testAddPatientPressBackPressYesCheckChangeActivity(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        onView(withText("Sí")).perform(click())
        onView(withId(R.id.anadirPaciente)).check(matches(isDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun testAddPatientPressBackPressNoCheckChangeActivity(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        onView(withText("No")).perform(click())
        onView(withText("Añadir cuenta")).check(matches(isDisplayed())).check(matches(isClickable()))
    }

    @Test
    fun testAddPatientSelfCheckErrorMessage(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signInEmailPatient)).perform(typeText("usuario1@gmail.com")) //preguntar contra
        closeSoftKeyboard()
        onView(withId(R.id.signInPasswordPatient)).perform(typeText("1234567890")) //cambiar contra
        closeSoftKeyboard()
        onView(withText("Añadir cuenta")).perform(click())
        Thread.sleep(1000)
        onView(withText("Error")).check(matches(isDisplayed()))
    }

    @Test
    fun testAddPatientCorrectSignInPatientCheck(){
        onView(withId(R.id.anadirPaciente)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signInEmailPatient)).perform(typeText("soyunyayo@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.signInPasswordPatient)).perform(typeText("1234567890"))
        closeSoftKeyboard()
        onView(withText("Añadir cuenta")).perform(click())
        Thread.sleep(1000)
        onView(withText("yayo, yayo")).check(matches(isDisplayed()))

    }


}