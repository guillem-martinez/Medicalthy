package com.grupo10.medicalthy

import java.util.concurrent.atomic.AtomicInteger

object RandomUtils {
    private val randomInt = AtomicInteger()

    fun getRandomInt() = randomInt.getAndIncrement() + System.currentTimeMillis().toInt()
}