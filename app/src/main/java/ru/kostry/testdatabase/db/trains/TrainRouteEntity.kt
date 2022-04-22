package ru.kostry.testdatabase.db.trains

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrainRouteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routeNumber: String,
    val destination: String,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    val totalTimeInMillis: Long = stop.timeInMillis - start.timeInMillis,
)
