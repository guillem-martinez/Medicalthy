package com.grupo10.medicalthy

object Constants {
    const val SET_EXACT_ALARM = "SET_EXACT_ALARM"
    const val SET_REPETITIVE_ALARM = "SET_REPETITIVE_ALARM"
    const val EXACT_ALARM_TIME = "EXACT_ALARM_TIME"
    const val NUM_DAYS = "NUM_DAYS"

    val CLASS_REF = "CLASS_REF"
    val HOME_REF = HomeActivity::class.java
    val SIGNUP_REF = SignUpActivity::class.java
    val PLAN_MEDICINE = PlanMedicineActivity::class.java
    val SHOW_SHOTS = ShowShotsActivity::class.java
    val SHOW_MEDICINE = ShowMedicineActivity::class.java

    enum class ActivityRef{
        HomeActivity,
        SignUpActivity,
        ShowShotsActivity,
        PlanMedicineActivity,
        ShowMedicineActivity
    }

    //Mapa con clave ordinal(enum ActivityRef) 0-n y valor Class<*> ejemplo: classRefMap[0] = HomeActivity::class.java
    val classRefMap = mapOf(
        ActivityRef.HomeActivity.ordinal to HOME_REF,
        ActivityRef.SignUpActivity.ordinal to SIGNUP_REF,
        ActivityRef.ShowShotsActivity.ordinal to SHOW_SHOTS,
        ActivityRef.PlanMedicineActivity.ordinal to PLAN_MEDICINE,
        ActivityRef.ShowMedicineActivity.ordinal to SHOW_MEDICINE
    )
}