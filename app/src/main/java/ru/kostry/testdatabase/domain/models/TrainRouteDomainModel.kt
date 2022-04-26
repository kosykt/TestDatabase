package ru.kostry.testdatabase.domain.models

import java.util.*

data class TrainRouteDomainModel(

    val id: Int = 0,
    val routeNumber: String,
    val destination: String,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    var personId: Int = 0,
    val totalTimeInMillis: Long = stop.timeInMillis - start.timeInMillis,
)
