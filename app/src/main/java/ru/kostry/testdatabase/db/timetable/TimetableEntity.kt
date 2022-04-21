package ru.kostry.testdatabase.db.timetable

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kostry.testdatabase.db.TimeInterval
import java.util.*

@Entity
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val personId: Int,
    val personName: String,
    val trainId: Int,
    val trainRoute: String,
    val interval: TimeInterval,
    val destination: String,
)
