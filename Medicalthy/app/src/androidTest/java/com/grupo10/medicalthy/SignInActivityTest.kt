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
class SignInActivityTest {
    /*
    @get:Rule
    val activityRule:ActivityScenarioRule<SignInActivity> = ActivityScenarioRule(SignInActivity::class.java)*/


    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<SignInActivity> = ActivityTestRule(SignInActivity::class.java, true, false)
    //val activityScenario = ActivittyScenario.launch()

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testInvalidMailChecksErrorMessage(){
        //val titleid = mActivityrule.activity.resources.getIdentifier("Error", "id", "android")
        val email:String = "soyunviejo@gmail.com"
        val password:String = "123456789"
        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Se ha producido un error al Iniciar Sesion.\n" +
                "Compruebe que el email tenga el siguiente estilo: ejemplo@gmail.com")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        //check notificaion y mensaje correctos
    }

    @Test
    fun testInvalidPasswordChecksErrorMessage(){
        val email:String = "soyunyayo@gmail.com"
        val password:String = "123456789"
        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Se ha producido un error al Iniciar Sesion.\n" +
                "Compruebe que el email tenga el siguiente estilo: ejemplo@gmail.com")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        //check notificaion y mensaje correctos
    }

    @Test
    fun testInvalidPasswordLengthChecksErrorMessage(){
        val email:String = "soyunyayo@gmail.com"
        val password:String = "12345"
        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("El tamaño de contraseña es demasiado corto (mínimo 6 caracteres). Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        //check notificaion y mensaje correctos
    }

    @Test
    fun testEmptyEmailChecksErrorMessage(){
        val email:String = ""
        val password:String = "123456789"
        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyPasswordChecksErrorMessage(){
        val email:String = "soyunyayo@gmail.com"
        val password:String = ""
        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }


    //falta cmabiar usuario a cuidador
    @Test
    fun testValidCuidadorLoginChecksCorrectHome(){
        val email:String = "soyunyayo@gmail.com"
        val password:String = "1234567890"
        //val activityScenario = ActivityScenario.launch(SignInActivity::class.java)

        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.btnShowShots)).check(matches(isDisplayed()))

    }

    @Test
    fun testValidPatientEmailChecksCorrectHome(){
        val email:String = "soyunyayo@gmail.com"
        val password:String = "1234567890"
        //val activityScenario = ActivityScenario.launch(SignInActivity::class.java)

        onView(withId(R.id.signInEmail)).perform(typeText(email))
        onView(withId(R.id.signInPassword)).perform(typeText(password))
        onView(withId(R.id.loginButton)).perform(click())
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.btnShowShotsPatient)).check(matches(isDisplayed()))
    }

    @Test
    fun testGoogleButtonChecksUsable(){
        onView(withId(R.id.googleButton)).check(matches(isDisplayed())).check(matches(isClickable()))
    }
}