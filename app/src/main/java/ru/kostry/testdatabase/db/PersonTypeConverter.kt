package ru.kostry.testdatabase.db

import androidx.room.TypeConverter
import java.util.stream.Collectors

class PersonTypeConverter {

    @TypeConverter
    fun toDaysOff(days: String): List<DayOffEntityModel> {
        val listString: List<String> = days.split(", ")
        val daysOff: MutableList<DayOffEntityModel> = mutableListOf()
        listString.forEach {
            val splitDate = getSplitDate(it)
            daysOff += DayOffEntityModel(
                day = splitDate[0].toInt(),
                month = splitDate[1].toInt(),
                year = splitDate[2].toInt(),
            )
        }
        return daysOff
    }

    private fun getSplitDate(string: String): List<String> {
        return string.split(".")
    }

    @TypeConverter
    fun fromDaysOff(daysOff: List<DayOffEntityModel>): String {
        return daysOff.stream()
            .map { "${it.day}.${it.month}.${it.year}" }
            .collect(Collectors.joining(", "))
    }

    @TypeConverter
    fun toPathDirections(path: String): List<PathDirectionEntityModel> {
        val listString: List<String> = path.split(",")
        val pathDirections: MutableList<PathDirectionEntityModel> = mutableListOf()
        listString.forEach {
            val splitValue = getSplitDestination(it)
            pathDirections += PathDirectionEntityModel(
                destination = splitValue[0],
                permission = splitValue[1].toBoolean()
            )
        }
        return pathDirections
    }

    private fun getSplitDestination(string: String): List<String> {
        return string.split("=")
    }

    @TypeConverter
    fun fromPathDirections(pathDirections: List<PathDirectionEntityModel>): String {
        return pathDirections.stream()
            .map { "${it.destination} = ${it.permission}" }
            .collect(Collectors.joining(","))
    }
}