package ru.kostry.testdatabase.db.exaples.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ThirdEntity(
    @PrimaryKey
    val id: Int,
    val myPackages: List<MyPackage>
)

data class MyPackage(
    val text: String
)
