package ru.kostry.testdatabase.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val hoursWorked: Int,
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
