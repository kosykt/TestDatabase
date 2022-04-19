package ru.kostry.testdatabase.db.exaples.converter

import androidx.room.TypeConverter
import java.util.stream.Collectors

class FirstTypeConverter {

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }

    @TypeConverter
    fun fromList(stringList: List<String>): String {
        return stringList.stream().collect(Collectors.joining(","))
    }
}