package com.grupo10.medicalthy

import android.widget.EditText
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AuthUnitTest {
    val authObj = Auth()

    //  SingUp Tests

    /*@Test
    fun testEmptyEmailExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("", "123456", "name", "surname", "1")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testEmptyPasswordExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "", "name", "surname", "1")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testEmptyNameExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "", "surname", "1")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testEmptySurnameExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "", "1")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testEmptyAgeExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testTooShortPasswordExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "12345", "name", "surname", "1")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testMinimumPasswordLengthExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "1")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testValidPasswordLengthExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "1234567", "name", "surname", "1")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testAveragePasswordLengthExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "1234567890", "name", "surname", "1")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testInvalidMinimumAgeExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "0")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testMinimumAgeExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "1")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testValidMinimumAgeExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "2")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testValidAverageAgeExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "40")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testValidMaximumAgeExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "119")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testMaximumAgeExpectsTrue(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "120")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testInvalidMaximumAgeExpectsFalse(){
        val result = authObj.verifyCredentialsSignUp("pablo@gmail.com", "123456", "name", "surname", "121")[0].toString()
        assertEquals("false", result)
    }


    //  SingIn Tests

    @Test
    fun testSingInEmptyEmailExpectsFalse(){
        val result = authObj.verifyCredentialsSignIn("", "123456")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testSingInEmptyPasswordExpectsFalse(){
        val result = authObj.verifyCredentialsSignIn("mail@gmail.com", "")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testSingInTooShortPasswordExpectsFalse(){
        val result = authObj.verifyCredentialsSignIn("mail@gmail.com", "12345")[0].toString()
        assertEquals("false", result)
    }

    @Test
    fun testSingInValidMinimumPasswordExpectsTrue(){
        val result = authObj.verifyCredentialsSignIn("mail@gmail.com", "123456")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testSingInValidPasswordExpectsTrue(){
        val result = authObj.verifyCredentialsSignIn("mail@gmail.com", "1234567")[0].toString()
        assertEquals("true", result)
    }

    @Test
    fun testSingInAveragePasswordExpectsTrue(){
        val result = authObj.verifyCredentialsSignIn("mail@gmail.com", "1234567890")[0].toString()
        assertEquals("true", result)
    }



*/


}