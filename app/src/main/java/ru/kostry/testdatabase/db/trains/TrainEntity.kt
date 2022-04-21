package ru.kostry.testdatabase.db.trains

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kostry.testdatabase.db.TimeInterval

@Entity
data class TrainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timetableId: Int = 0,
    val routeNumber: String,
    val time: TimeInterval,
    val destination: String,
)
