package ru.kostry.testdatabase.db

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.persons.PathDirectionEntityModel
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

class PersonTypeConverter {

    @TypeConverter
    fun toListPathDirections(path: String): List<PathDirectionEntityModel> {
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
    fun fromListPathDirections(pathDirections: List<PathDirectionEntityModel>): String {
        return pathDirections.stream()
            .map { "${it.destination}=${it.permission}" }
            .collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toListGregorianCalendar(text: String): List<GregorianCalendar> {
        val splitList: List<String> = text.split("/")
        return splitList.stream()
            .map { toGregorianCalendar(it) }
            .toList()
    }

    @TypeConverter
    fun fromListGregorianCalendar(listDate: List<GregorianCalendar>): String {
        return listDate.stream()
            .map { fromGregorianCalendar(it) }
            .collect(Collectors.joining("/"))
    }

    @TypeConverter
    fun toGregorianCalendar(text: String): GregorianCalendar {
        val dateList: List<String> = text.split(",")
        return GregorianCalendar().apply {
            this.set(Calendar.YEAR, dateList[0].toInt())
            this.set(Calendar.MONTH, dateList[1].toInt())
            this.set(Calendar.DAY_OF_MONTH, dateList[2].toInt())
            this.set(Calendar.HOUR_OF_DAY, dateList[3].toInt())
            this.set(Calendar.MINUTE, dateList[4].toInt())
        }
    }

    @TypeConverter
    fun fromGregorianCalendar(gregorianCalendar: GregorianCalendar): String {
        return "${gregorianCalendar.get(Calendar.YEAR)}," +
                "${gregorianCalendar.get(Calendar.MONTH)}," +
                "${gregorianCalendar.get(Calendar.DAY_OF_MONTH)}," +
                "${gregorianCalendar.get(Calendar.HOUR_OF_DAY)}," +
                "${gregorianCalendar.get(Calendar.MINUTE)}"
    }
}