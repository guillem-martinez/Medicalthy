package com.grupo10.medicalthy

import org.junit.Assert.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.PressBackAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
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

@RunWith(AndroidJUnit4::class)
class ShoppingListTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }
   //margin top for addmedicine button : 46
   //margin top for btn shopping list : 20

    @Test
    fun testAddEmptyProductNameCheckErrorMessage(){
        onView(withId(R.id.btnShoppingCart)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btnAnadirProducto)).perform(click())
        Thread.sleep(1000)
        /*onView(withText("Añadir Producto")).perform(typeText(""))
        closeSoftKeyboard()*/
        onView(withText("AÑADIR")).perform(click())
        Thread.sleep(1000)
        onView(withText("Alerta")).check(matches(isDisplayed()))
        onView(withText("El producto debe contener al menos un carácter")).check(matches(isDisplayed()))
    }

    @Test
    fun testAddProductCheckListAfter(){
        onView(withId(R.id.btnShoppingCart)).perform(click())
        onView(withId(R.id.btnAnadirProducto)).perform(click())
        Thread.sleep(1000)
        onView(withHint("Introduzca el producto")).inRoot(isDialog()).perform(typeText("Paracetamol"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withText("AÑADIR")).perform(click())
        Thread.sleep(1000)
        onView(withText("Paracetamol")).check(matches(isDisplayed()))
    }

    //height linear layout lista compra original: 443, set to 375
    @Test
    fun testEmptyListCheckAfter(){
        onView(withId(R.id.btnShoppingCart)).perform(click())
        onView(withId(R.id.btnEmptyList)).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        onView(withId(R.id.btnShoppingCart)).perform(click())
        onView(withText("Paracetamol")).check(doesNotExist())
    }


}