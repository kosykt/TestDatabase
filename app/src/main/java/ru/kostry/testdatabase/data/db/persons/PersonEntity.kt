package ru.kostry.testdatabase.data.db.persons

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kostry.testdatabase.utils.models.PersonTimeInterval
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
    val busyTime: MutableList<PersonTimeInterval> = mutableListOf(),
    var workingMillis: Long = busyTime
        .stream()
        .map { it.millis }
        .collect(Collectors.toList())
        .sum()
){
    fun refreshWorkingMillis(){
        workingMillis = busyTime
            .stream()
            .map { it.millis }
            .collect(Collectors.toList())
            .sum()
    }
}