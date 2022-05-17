package com.grupo10.medicalthy

import org.junit.Test

import org.junit.Assert.*

class UserUnitTest {
    var productlist= mutableListOf<Product>()

    val usertest = User("test", "a", "b", 1, false, productlist, null)

    @Test
    fun testEmptyNameExpectsError(){
        usertest.añadirMedicamentos("", "123456789", 30, 2)
    }
}