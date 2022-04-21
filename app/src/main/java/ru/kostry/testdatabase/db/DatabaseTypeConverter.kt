package ru.kostry.testdatabase.db

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.trains.TrainCheckOutEntity
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

    @TypeConverter
    fun toListTrainCheckOutEntity(text: String): MutableList<TrainCheckOutEntity> {
        val splitList: List<String> = text.split("/")
        val returnedData: MutableList<TrainCheckOutEntity> = mutableListOf()
        splitList.forEach { str ->
            val subList: List<String> = str.split("=")
            val gregorianCalendar = toGregorianCalendar(subList[0])
            returnedData.add(TrainCheckOutEntity(gregorianCalendar, subList[1].toInt()))
        }
        return returnedData
    }

    @TypeConverter
    fun fromListTrainCheckOutEntity(checkOuts: List<TrainCheckOutEntity>): String {
         return checkOuts.stream()
            .map { "${fromGregorianCalendar(it.time)}=${it.personEntityId}" }
            .collect(Collectors.joining("/"))
    }
}