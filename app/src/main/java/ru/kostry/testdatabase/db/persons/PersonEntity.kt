package ru.kostry.testdatabase.db.persons

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var trainNumber: Int = 0,
    var readyToRide: Boolean,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    var hoursWorked: Int,
    val daysOff: List<DayOffEntityModel>,
    val pathDirections: List<PathDirectionEntityModel>,
)

data class PathDirectionEntityModel(
    val destination: String,
    val permission: Boolean,
)

data class DayOffEntityModel(
    val day: Int,
    val month: Int,
    val year: Int,
)
