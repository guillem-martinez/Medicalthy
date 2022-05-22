package com.grupo10.medicalthy

import org.junit.Test
import org.junit.Assert.*

class TakePicTest {
    var take_pic = TakePictureActivity()

    @Test
    fun testEmptyEmailExpectsFalse() {
        val texto = "Hola esto es una prueba\nEsta prueba contiene un numero de 7 digitos\n 666666.6"
        val respuesta = "666666.6"
        val valor = take_pic.getNC(texto)
        assertEquals(valor, respuesta)
    }

    /*@Test
    fun testNC_6_Digitos() {
        val texto = "Hola esto es una prueba\nEsta prueba contiene un numero de 6 digitos\n 666666"
        val respuesta = "666666"
        assertEquals(take_pic.getNC(texto), respuesta)
    }

    @Test
    fun testNC_notFound() {
        val texto = "Hola esto es una prueba\nEsta prueba no contiene digitos"
        val respuesta = "ERROR"
        assertEquals(take_pic.getNC(texto), respuesta)
    }*/

}