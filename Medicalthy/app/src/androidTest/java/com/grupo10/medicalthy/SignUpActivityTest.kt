package com.grupo10.medicalthy

import org.junit.Assert.*
import org.junit.Assert.*
import android.content.Intent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<SignUpActivity> = ActivityTestRule(SignUpActivity::class.java, true, false)

    //usuario testing:

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testInvalidEmailChecksErrorMessage(){
        val email:String = "correonovalido"
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Se ha producido un error al Iniciar Sesion.\n" +
                "Compruebe que el email tenga el siguiente estilo: ejemplo@gmail.com")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))

    }

    @Test
    fun testEmptyEmailChecksErrorMessage(){
        val email:String = ""
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyPasswordChecksErrorMessage(){
        val email:String = "usuariotest@gmail.com"
        val password:String = ""
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyNameChecksErrorMessage(){
        val email:String = "usuariotest@gmail.com"
        val password:String = "123456"
        val name:String = ""
        val surname:String = "Espresso"
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptySurnameChecksErrorMessage(){
        val email:String = "usuariotest@gmail.com"
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = ""
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyAgeChecksErrorMessage(){
        val email:String = "usuariotest@gmail.com"
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age:String = ""

        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        closeSoftKeyboard()
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("Correo electrónico i/o contraseña vacíos. Por favor, ingrese el texto restante.")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    //stand by
    @Test
    fun testInvalidAgeChecksErrorMessage(){
        val email:String = "usuariotest@gmail.com"
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age = "400"

        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        closeSoftKeyboard()
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withText("Error")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
        onView(withText("La edad seleccionada no es válida. Por favor, seleccione una edad entre 1 y 120 .")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testValidCredentialsChecksGoHome(){
        val email:String = "usuariotest@gmail.com"
        val password:String = "123456"
        val name:String = "UsuarioTest"
        val surname:String = "Espresso"
        val age:String = "20"

        onView(withId(R.id.signUpEmail)).perform(typeText(email))
        onView(withId(R.id.signUpPassword)).perform(typeText(password))
        onView(withId(R.id.editTextName)).perform(typeText(name))
        onView(withId(R.id.editTextSurname)).perform(typeText(surname))
        onView(withId(R.id.editTextAge)).perform(typeText(age))
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withId(R.id.btnAddMedicine)).check(matches(isDisplayed()))
    }




}