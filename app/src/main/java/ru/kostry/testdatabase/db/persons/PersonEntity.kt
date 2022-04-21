package ru.kostry.testdatabase.db.persons

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kostry.testdatabase.db.TimeInterval
import java.util.*

@Entity
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workingHours: List<TimeInterval> = emptyList(),
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val daysOff: List<GregorianCalendar>,
    val pathDirections: List<Map<String, Boolean>>,
)