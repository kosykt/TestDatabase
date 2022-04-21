package ru.kostry.testdatabase.db.trains

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val checkOuts: List<TrainCheckOutEntity>,
)

data class TrainCheckOutEntity(
    val time: GregorianCalendar,
    val destination: String,
    val workingHours: Int,
    val personEntityId: Int = 0,
    var isBusy: Boolean = false,
)
