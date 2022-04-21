package ru.kostry.testdatabase.db.timetable

import androidx.room.*

@Dao
interface TimetableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: TimetableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: List<TimetableEntity>)

    @Delete
    fun delete(model: TimetableEntity)

    @Query("SELECT * FROM TimetableEntity")
    fun getAll(): List<TimetableEntity>
}