package ru.kostry.testdatabase.db

import androidx.room.*

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: PersonEntity)

    @Delete
    fun delete(model: PersonEntity)

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): List<PersonEntity>
}