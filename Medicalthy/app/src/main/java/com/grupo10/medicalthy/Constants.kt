package com.grupo10.medicalthy

object Constants {
    val SET_EXACT_ALARM = "SET_EXACT_ALARM"
    val SET_REPETITIVE_ALARM = "SET_REPETITIVE_ALARM"
    val EXACT_ALARM_TIME = "EXACT_ALARM_TIME"

    val CLASS_REF = "CLASS_REF"
    val HOME_REF = HomeActivity::class.java
    val SIGNUP_REF = SignUpActivity::class.java

    enum class ActivityRef{
        HomeActivity,
        SignUpActivity
    }

    val classRefMap = mapOf(
        ActivityRef.HomeActivity.ordinal to HOME_REF,
        ActivityRef.SignUpActivity.ordinal to SIGNUP_REF
    )
}