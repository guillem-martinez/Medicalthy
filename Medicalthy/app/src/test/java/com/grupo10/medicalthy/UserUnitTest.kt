package com.grupo10.medicalthy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class UserUnitTest {
    var productlist= mutableListOf<Product>()
    val usertest = User("testUser", "Usuario", "Prueba", 1, false, productlist, null)

    val ibuprofeno = listOf("Ibuprofeno", "1234567", 30, 1)
    val paracetamol = listOf("Paracetamol", "7654321", 30, 1)
    val otro = listOf("Otros", "7777777", 30, 1)
    val medicines = listOf(ibuprofeno, paracetamol, otro)

    @Test
    fun test_añadirUnMedicamento() {
        val ibu = medicines[0]
        usertest.añadirMedicamentos(ibu[0] as String, ibu[1] as String, ibu[2] as Int, ibu[3] as Int)
        val user_products = usertest.getProducts()
        assertEquals(user_products.size, 1)
    }

    @Test
    fun test_unProductoAñadido() {
        val ibu = medicines[0]
        usertest.añadirMedicamentos(ibu[0] as String, ibu[1] as String, ibu[2] as Int, ibu[3] as Int)
        val user_products = usertest.getProducts()
        val first_product = user_products[0]
        assertEquals(first_product.name, ibu[0])
        assertEquals(first_product.cnn, ibu[1])
        assertEquals(first_product.nComprimidos, ibu[2])
        assertEquals(first_product.frecuency, ibu[3])
    }

    @Test
    fun test_añadirVariosMedicamentos() {
        for (i in 0..medicines.size - 1) {
            val medicine = medicines[i]
            usertest.añadirMedicamentos(medicine[0] as String, medicine[1] as String, medicine[2] as Int, medicine[3] as Int)
        }
        val user_products = usertest.getProducts()
        assertEquals(user_products.size, 3)
    }

    @Test
    fun test_variosProductosAñadidos() {
        for (i in 0..medicines.size - 1) {
            val medicine = medicines[i]
            usertest.añadirMedicamentos(medicine[0] as String, medicine[1] as String, medicine[2] as Int, medicine[3] as Int)
        }
        val user_products = usertest.getProducts()
        for (i in 0..user_products.size - 1) {
            assertEquals(user_products[i].name, medicines[i][0])
            assertEquals(user_products[i].cnn, medicines[i][1])
            assertEquals(user_products[i].nComprimidos, medicines[i][2])
            assertEquals(user_products[i].frecuency, medicines[i][3])
        }
    }
}