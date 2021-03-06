package ru.kostry.testdatabase.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FirstEntity(
    @PrimaryKey
    val id: Int,
    val text: String,
    val stringList: List<String>,
)