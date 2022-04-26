package ru.kostry.testdatabase.utils.models

import java.util.*

data class PersonTimeInterval(
    val trainRoute: String,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    val millis: Long = stop.timeInMillis - start.timeInMillis,
)