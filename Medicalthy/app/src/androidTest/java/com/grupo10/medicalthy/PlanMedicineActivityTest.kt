package com.grupo10.medicalthy

import android.app.Activity
import android.app.Instrumentation
import android.app.TimePickerDialog
import android.content.ContentResolver
import org.junit.Assert.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TimePicker
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matcher

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class PlanMedicineActivityTest {

    @Rule
    @JvmField
    val intentsTestRule: IntentsTestRule<HomeActivity> = IntentsTestRule(HomeActivity::class.java)

    //original margin top for imageView4 10
    //original margin bottom for imageView4 10
    //original margin top for button2 3
    //original margin top for linearlayout4 10
    //original margin top for btnaddmedicine 46
    //Note
    @Test
    fun testTakePictureChecksIntent(){
        val activityResult = createImageCaptureActivityResultStub()
        val expectedIntent: Matcher<Intent> = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)

        onView(withId(R.id.btnAddMedicine)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button)).perform(click())
        intending(expectedIntent)
        Thread.sleep(1000)
    }

    @Test
    fun testTakePictureAddMedicineCheckCorrectInitialValues(){
        val activityResult = createImageCaptureActivityResultStub()
        val expectedIntent: Matcher<Intent> = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)

        onView(withId(R.id.btnAddMedicine)).perform(click())
        //navegamos a añadir medicamento
        onView(withId(R.id.button)).perform(click())
        //tomamos foto medicamento
        intending(expectedIntent)
        Thread.sleep(2000)
        onView(withText("Ibuprofeno Kern Pharma")).check(matches(isDisplayed()))
        onView(withText("El codigo nacional es: 857979.2")).check(matches(isDisplayed()))

    }

    @Test
    fun testTakePictureAddMedicineCheckConsumoResponsableIsDisplayed(){
        val activityResult = createImageCaptureActivityResultStub()
        val expectedIntent: Matcher<Intent> = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)
        val calendar = Calendar.getInstance()

        onView(withId(R.id.btnAddMedicine)).perform(click())
        //navegamos a añadir medicamento
        onView(withId(R.id.button)).perform(click())
        //tomamos foto medicamento
        intending(expectedIntent)
        Thread.sleep(2000)
        onView(withId(R.id.addDate)).perform(click())
        //añadimos fecha inial
        Thread.sleep(2000)
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        onView(withText("El dia de inicio es:  28/05/2022")).check(matches(isDisplayed()))
        //check de la fecha, nota: solo funcionara el dia seleccionado
        onView(withId(R.id.nComprimidos)).perform(typeText("20"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.numDays)).perform(typeText("10"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.addHour)).perform(click())
        Thread.sleep(1000)
        onView(isAssignableFrom(TimePicker::class.java)).perform(
            PickerActions.setTime(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
        )
        onView(withText("OK")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.addHour)).perform(click())
        Thread.sleep(1000)
        onView(isAssignableFrom(TimePicker::class.java)).perform(
            PickerActions.setTime(
                calendar.get(Calendar.HOUR_OF_DAY)+2,
                calendar.get(Calendar.MINUTE)
            )
        )
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.saveButton)).perform(scrollTo()).perform(click())
        Thread.sleep(1000)
        onView(withText("Consumo Responsable")).check(matches(isDisplayed()))
    }

    @Test
    fun testTakePictureAddMedicineCheckCorrectValuesAndShowShots(){
        val activityResult = createImageCaptureActivityResultStub()
        val expectedIntent: Matcher<Intent> = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)
        val calendar = Calendar.getInstance()

        onView(withId(R.id.btnAddMedicine)).perform(click())
        //navegamos a añadir medicamento
        onView(withId(R.id.button)).perform(click())
        //tomamos foto medicamento
        intending(expectedIntent)
        Thread.sleep(1000)
        onView(withId(R.id.addDate)).perform(click())
        //añadimos fecha inial
        Thread.sleep(1000)
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        onView(withText("El dia de inicio es:  28/05/2022")).check(matches(isDisplayed()))
        //check de la fecha, nota: solo funcionara el dia seleccionado
        onView(withId(R.id.nComprimidos)).perform(typeText("20"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.numDays)).perform(typeText("10"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.addHour)).perform(click())
        Thread.sleep(1000)
        onView(withText("OK")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.saveButton)).perform(scrollTo()).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btnShowShots)).perform(click())
        Thread.sleep(1000)
        onView(withText("Ibuprofeno Kern Pharma -> " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE))).check(matches(
            isDisplayed()))//mirar
        Thread.sleep(10000)


    }

    @Test
    fun testTakePictureAddMedicineWrongPictureChecksErrorMessage(){
        val activityResult = createImageCaptureWrongActivityResultStub()
        val expectedIntent: Matcher<Intent> = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)
        onView(withId(R.id.btnAddMedicine)).perform(click())
        //navegamos a añadir medicamento
        onView(withId(R.id.button)).perform(click())
        //tomamos foto medicamento
        intending(expectedIntent)
        Thread.sleep(1000)
        onView(withText("ERROR, no se ha leido bien la imagen ")).check(matches(isDisplayed()))
        //comprobamos mensaje de error para el ocr fallido

    }

    private fun createImageCaptureActivityResultStub(): Instrumentation.ActivityResult?{
        val bundle = Bundle()
        bundle.putParcelable(
            "data",
            MediaStore.Images.Media.getBitmap(intentsTestRule.activity.contentResolver,
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +"://"+
                    intentsTestRule.activity.resources.getResourcePackageName(R.drawable.prueba2)+ "/" +
                    intentsTestRule.activity.resources.getResourceTypeName(R.drawable.prueba2)+ "/" +
                    intentsTestRule.activity.resources.getResourceEntryName(R.drawable.prueba2)
            ))
        )
        val resultdata = Intent()
        resultdata.putExtras(bundle)
        return Instrumentation.ActivityResult(Activity.RESULT_OK, resultdata)

    }

    private fun createImageCaptureWrongActivityResultStub(): Instrumentation.ActivityResult?{
        val bundle = Bundle()
        bundle.putParcelable(
            "data",
            MediaStore.Images.Media.getBitmap(intentsTestRule.activity.contentResolver,
                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +"://"+
                        intentsTestRule.activity.resources.getResourcePackageName(R.drawable.prueba2_w)+ "/" +
                        intentsTestRule.activity.resources.getResourceTypeName(R.drawable.prueba2_w)+ "/" +
                        intentsTestRule.activity.resources.getResourceEntryName(R.drawable.prueba2_w)
                ))
        )
        val resultdata = Intent()
        resultdata.putExtras(bundle)
        return Instrumentation.ActivityResult(Activity.RESULT_OK, resultdata)

    }
}