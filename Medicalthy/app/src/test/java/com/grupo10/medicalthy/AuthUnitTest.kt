package com.grupo10.medicalthy

import android.widget.EditText
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AuthUnitTest {
    val authObj = Auth()

    private companion object {
        @JvmStatic
        fun getSignUpValues() = Stream.of(
            Arguments.of("", "123456", "name", "surname", "1", false, "empty"),
            Arguments.of("prueba@gmail.com", "", "name", "surname", "1", false, "empty"),
            Arguments.of("prueba@gmail.com", "123456", "", "surname", "1", false, "empty"),
            Arguments.of("prueba@gmail.com", "123456", "name", "", "1", false, "empty"),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "", false, "empty"),
            Arguments.of("prueba@gmail.com", "12345", "name", "surname", "1", false, "length"),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "0", false, "age"),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "-1", false, "age"),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "121", false, "age"),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "120", true, ""),
            Arguments.of("prueba@gmail.com", "123456", "name", "surname", "1", true, "")
        )

        @JvmStatic
        fun getSignInValues() = Stream.of(
            Arguments.of("", "123456", false, "empty"),
            Arguments.of("prueba@gmail.com", "", false, "empty"),
            Arguments.of("prueba@gmail.com", "12345", false, "length"),
            Arguments.of("prueba@gmail.com", "123456", true, "")
        )
    }

    //  SingUp Tests
    @ParameterizedTest
    @MethodSource("getSignUpValues")
    fun test_verifyCredentialsSignUp(
        email: String,
        pass: String,
        name: String,
        surname: String,
        age: String,
        success: Boolean,
        error: String
    ) {
        val response = authObj.verifyCredentialsSignUp(email, pass, name, surname, age)
        assertEquals(response[0], success)
        assertEquals(response[1], error)
    }

    //  SingIn Tests
    @ParameterizedTest
    @MethodSource("getSignInValues")
    fun test_verifyCredentialsSignIn(
        email: String,
        pass: String,
        success: Boolean,
        error: String
    ) {
        val response = authObj.verifyCredentialsSignIn(email, pass)
        assertEquals(response[0], success)
        assertEquals(response[1], error)
    }
}