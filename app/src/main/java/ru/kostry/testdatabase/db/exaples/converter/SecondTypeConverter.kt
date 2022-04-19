package ru.kostry.testdatabase.db.exaples.converter

import androidx.room.TypeConverter
import ru.kostry.testdatabase.db.exaples.model.Box

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