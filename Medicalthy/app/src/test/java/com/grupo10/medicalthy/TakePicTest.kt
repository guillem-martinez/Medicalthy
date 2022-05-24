package com.grupo10.medicalthy

import com.grupo10.medicalthy.RandomUtils.getNCFromString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TakePicTest {

    private companion object {
        @JvmStatic
        fun getNCValues() = Stream.of(
            Arguments.of("Esta prueba contiene un numero de 7 digitos\n 666666.6", "666666.6"),
            Arguments.of("Esta prueba contiene un numero de 6 digitos\n 666666", "666666"),
            Arguments.of("Esta prueba no contiene digitos", "ERROR"),
            Arguments.of("Esta prueba contiene demasiados digitos\n 12345678", "ERROR"),
            Arguments.of("Esta prueba contiene pocos digitos\n 12345", "ERROR"),
            Arguments.of("Esta prueba contiene digitos separados 123\n 4567", "ERROR"),
            Arguments.of("Esta prueba contiene digitos separados 123\n 456", "ERROR")
        )
    }

    @ParameterizedTest(name="Prueba")
    @MethodSource("getNCValues")
    fun test_getNCFromString(texto:String, respuesta:String) {
        val valor = getNCFromString(texto)
        Assertions.assertEquals(valor, respuesta)
    }
}