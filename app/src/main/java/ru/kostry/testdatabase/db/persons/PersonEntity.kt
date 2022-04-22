package ru.kostry.testdatabase.db.persons

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.stream.Collectors

@Entity
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val daysOff: List<GregorianCalendar>,
    val pathDirections: List<Map<String, Boolean>>,
    val busyTime: MutableList<Interval> = mutableListOf(),
    val workingMillis: Long = busyTime
        .stream()
        .map { it.millis }
        .collect(Collectors.toList())
        .sum()
)

data class Interval(
    val trainRoute: String,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    val millis: Long = stop.timeInMillis - start.timeInMillis
)