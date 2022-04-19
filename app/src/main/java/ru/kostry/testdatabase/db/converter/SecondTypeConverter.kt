package ru.kostry.testdatabase.db.converter

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.model.Box

class SecondTypeConverter {

    @TypeConverter
    fun toBox(string: String): Box {
        return Box(name = string)
    }

    @TypeConverter
    fun fromBox(box: Box): String {
        return box.name
    }
}