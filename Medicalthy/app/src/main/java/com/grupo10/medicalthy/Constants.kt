package com.grupo10.medicalthy

object Constants {
    val SET_EXACT_ALARM = "SET_EXACT_ALARM"
    val SET_REPETITIVE_ALARM = "SET_REPETITIVE_ALARM"
    val EXACT_ALARM_TIME = "EXACT_ALARM_TIME"

    val CLASS_REF = "CLASS_REF"
    val HOME_REF = HomeActivity::class.java
    val SIGNUP_REF = SignUpActivity::class.java
    val PLAN_MEDICINE = PlanMedicineActivity::class.java
    val SHOW_SHOTS = ShowShotsActivity::class.java

    enum class ActivityRef{
        HomeActivity,
        SignUpActivity,
        ShowShotsActivity,
        PlanMedicineActivity
    }

    //Mapa con clave ordinal(enum ActivityRef) 0-n y valor Class<*> ejemplo: classRefMap[0] = HomeActivity::class.java
    val classRefMap = mapOf(
        ActivityRef.HomeActivity.ordinal to HOME_REF,
        ActivityRef.SignUpActivity.ordinal to SIGNUP_REF,
        ActivityRef.ShowShotsActivity.ordinal to SHOW_SHOTS,
        ActivityRef.PlanMedicineActivity.ordinal to PLAN_MEDICINE
    )
}