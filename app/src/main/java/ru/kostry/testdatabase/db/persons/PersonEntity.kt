package ru.kostry.testdatabase.db.persons

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    var hoursWorked: Int,
    val daysOff: List<GregorianCalendar>,
    val pathDirections: List<Map<String, Boolean>>,
)
