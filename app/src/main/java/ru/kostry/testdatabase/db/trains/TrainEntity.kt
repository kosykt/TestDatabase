package ru.kostry.testdatabase.db.trains

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val date: GregorianCalendar,
    val destination: String,
    val workingHours: Int,
    var isBusy: Boolean,
)