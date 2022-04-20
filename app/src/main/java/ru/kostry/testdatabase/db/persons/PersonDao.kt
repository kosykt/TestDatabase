package ru.kostry.testdatabase.db.persons

import androidx.room.*

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: PersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: List<PersonEntity>)

    @Delete
    fun delete(model: PersonEntity)

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): List<PersonEntity>

    @Query("SELECT * FROM PersonEntity WHERE readyToRide = 1 ORDER BY hoursWorked ASC")
    fun getReadyOrderedByHoursAsc(): List<PersonEntity>
}