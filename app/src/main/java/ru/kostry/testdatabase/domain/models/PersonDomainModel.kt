package ru.kostry.testdatabase.domain.models

import ru.kostry.testdatabase.utils.models.PersonTimeInterval
import java.util.*
import java.util.stream.Collectors

data class PersonDomainModel(
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
)

