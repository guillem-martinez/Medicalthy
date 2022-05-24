package com.grupo10.medicalthy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class UserUnitTest {
    var productlist= mutableListOf<Product>()

    val usertest = User("test", "a", "b", 1, false, productlist, null)

    @Test
    fun testEmptyNameExpectsError(){
        usertest.añadirMedicamentos("", "123456789", 30, 2)
    }
}