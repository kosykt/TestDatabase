package ru.kostry.testdatabase.data.db.trains

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
    var personId: Int = 0,
    val totalTimeInMillis: Long = stop.timeInMillis - start.timeInMillis,
)