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
    val destination: String,
    val workingHours: Int,
    var isBusy: Boolean,
)

data class TrainCheckOutEntity(
    val time: GregorianCalendar,
    val personEntityId: Int = 0,
)
