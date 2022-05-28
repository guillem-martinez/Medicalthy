package com.grupo10.medicalthy

import org.junit.Assert.*
import android.content.Intent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
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
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityrule:ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java, true, false)
    @Before
    fun setUp() {
        val intent = Intent()
        mActivityrule.launchActivity(intent)
    }

    @Test
    fun testShowShotsCheckChangesActivity(){
        //closeSoftKeyboard()
        onView(withId(R.id.btnShowShots)).perform(click())
        onView(withText("Tu medicación")).check(matches(isDisplayed()))
    }


    //Note: set the margin top 300 in the layout activity_home_hor (original 250) for the button btnAddMedicine,this is due to the view not being scrollable in espresso
    //Note: You may have to give permission to the app for it to use the camera
    @Test
    fun testAddMedicineCheckChangesActivity(){
        Thread.sleep(1000)
        onView(withId(R.id.btnAddMedicine)).perform(click())
        onView(withText("Escanee el Código Nacional de la caja del medicamento")).check(matches(
            isDisplayed()))
    }

    @Test
    fun testPharmacyMapChecksClickable(){
        onView(withId(R.id.btnPharmacy)).check(matches(isDisplayed())).check(matches(isClickable()))
        /*onView(withId(R.id.btnPharmacy)).perform(click())
        Thread.sleep(1000)*/
    }

    @Test
    fun testAdminPacientCheckChangesActivity(){
        onView(withId(R.id.btnAdminPerson)).perform(click())
        onView(withId(R.id.anadirPaciente)).check(matches(isDisplayed()))
    }

    @Test
    fun testMedicationHistoryCheckChangesActivity(){
        onView(withId(R.id.btnMedicationHistory)).perform(click())
        //Thread.sleep(1000)
        onView(withId(R.id.vertical_layout)).check(matches(isDisplayed()))
    }

    //Note: set the margin bottom 350(original 250) for the btnShoppingCart, this is due to the view not being scrollable in espresso
    @Test
    fun testShoppingCardChecksChangeActivity(){
        //Thread.sleep(1000)
        onView(withId(R.id.btnShoppingCart)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btnAnadirProducto)).check(matches(isDisplayed())).check(matches(
            isClickable()))
        onView(withId(R.id.btnEmptyList)).check(matches(isDisplayed())).check(matches(isClickable()))

    }

}