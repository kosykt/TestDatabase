package ru.kostry.testdatabase.db

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.persons.Interval
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

class DatabaseTypeConverter {

    @TypeConverter
    fun toListPathDirections(text: String): List<Map<String, Boolean>> {
        val splitList = text.split("/")
        val returnedData: MutableList<Map<String, Boolean>> = mutableListOf()
        splitList.forEach { str: String ->
            val a: List<String> = str
                .substringAfter("{")
                .substringBefore("}")
                .split("=")
            returnedData.add(mapOf(a[0] to a[1].toBoolean()))
        }
        return returnedData
    }

    @TypeConverter
    fun fromListPathDirections(path: List<Map<String, Boolean>>): String {
        return path.stream()
            .map { it.toString() }
            .collect(Collectors.joining("/"))
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
    fun toInterval(text: String): Interval {
        val split = text.split("=")
        return Interval(
            trainRoute = split[0],
            start = toGregorianCalendar(split[1]),
            stop = toGregorianCalendar(split[2]),
            millis = split[3].toLong()
        )
    }

    @TypeConverter
    fun fromInterval(interval: Interval): String {
        val route = interval.trainRoute
        val start = fromGregorianCalendar(interval.start)
        val stop = fromGregorianCalendar(interval.stop)
        val minutes = interval.millis
        return "$route=$start=$stop=$minutes"
    }

    @TypeConverter
    fun toListInterval(text: String): List<Interval> {
        if (text.isEmpty()){
            return emptyList()
        }
        val split = text.split("/")
        return split.stream()
            .map {
                toInterval(it)
            }
            .collect(Collectors.toList())
    }

    @TypeConverter
    fun fromListInterval(intervalList: List<Interval>): String {
        if (intervalList.isEmpty()){
            return ""
        }
        return intervalList.stream()
            .map {
                fromInterval(it)
            }
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