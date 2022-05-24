package com.grupo10.medicalthy

import com.grupo10.medicalthy.RandomUtils.getNCFromString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TakePicTest {

    @Test
    fun testEmptyEmailExpectsFalse() {
        val texto = "Hola esto es una prueba\nEsta prueba contiene un numero de 7 digitos\n 666666.6"
        val respuesta = "666666.6"
        val valor = getNCFromString(texto)
        assertEquals(valor, respuesta)
    }

    @Test
    fun testNC_6_Digitos() {
        val texto = "Hola esto es una prueba\nEsta prueba contiene un numero de 6 digitos\n 666666"
        val respuesta = "666666"
        val valor = getNCFromString(texto)
        assertEquals(valor, respuesta)
    }

    @Test
    fun testNC_notFound() {
        val texto = "Hola esto es una prueba\nEsta prueba no contiene digitos"
        val respuesta = "ERROR"
        val valor = getNCFromString(texto)
        assertEquals(valor, respuesta)
    }

}