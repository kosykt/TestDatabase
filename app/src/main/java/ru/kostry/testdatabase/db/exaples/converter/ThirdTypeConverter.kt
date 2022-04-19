package ru.kostry.testdatabase.db.exaples.converter

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.exaples.model.MyPackage
import java.util.stream.Collectors

class ThirdTypeConverter {

    @TypeConverter
    fun toMyPackage(string: String): List<MyPackage> {
        val a: List<String> = string.split(",")
        val b: MutableList<MyPackage> = mutableListOf()
        a.forEach { element ->
            b += MyPackage(element)
        }
        return b
    }

    @TypeConverter
    fun fromMyPackage(myPackageList: List<MyPackage>): String {
        return myPackageList.stream()
            .map { it.text }
            .collect(Collectors.joining(","))
    }
}