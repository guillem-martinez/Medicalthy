package com.grupo10.medicalthy

import junit.framework.Assert.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TakePictureTest {
    var take_pic = TakePictureActivity()

    @ParameterizedTest(name = "testEmptyNameExpectsError")
    @MethodSource("nationalCodeArguments")
    fun testEmptyNameExpectsError(text:String, nc:String) {
        assertEquals(take_pic.getNC(text), nc)
    }

    private companion object{
        @JvmStatic
        fun nationalCodeArguments() = Stream.of(
            Arguments.of("Hola, esto es una prueba\n Esta prueba contiene un numero de 7 digitos \n 666666.6", "666666.6"),
            Arguments.of("Hola, esto es una prueba\n Esta prueba contiene un numero de 6 digitos \n 666666", "666666"),
            Arguments.of("Hola, esto es una prueba\n Esta prueba no contiene digitos", "ERROR")
        )
    }
}