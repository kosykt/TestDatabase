package ru.kostry.testdatabase.db.dao

import androidx.room.*
import ru.kostry.testdatabase.db.model.FirstEntity

@Dao
interface FirstDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: FirstEntity)

    @Delete
    fun delete(model: FirstEntity)

    @Query("SELECT * FROM FirstEntity")
    fun getAll(): List<FirstEntity>
}