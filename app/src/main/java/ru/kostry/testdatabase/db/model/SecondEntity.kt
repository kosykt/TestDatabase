package ru.kostry.testdatabase.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SecondEntity(
    @PrimaryKey
    val id: Int,
    val box: Box
)

data class Box(
    val name: String
)