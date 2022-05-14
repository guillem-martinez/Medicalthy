package com.grupo10.medicalthy

import java.util.concurrent.atomic.AtomicInteger

object RandomUtils {
    private val randomInt = AtomicInteger()

    fun getRandomInt() = randomInt.getAndIncrement() + System.currentTimeMillis().toInt()

    fun dateFormatter(timeInMillis: Long): String =
        android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}