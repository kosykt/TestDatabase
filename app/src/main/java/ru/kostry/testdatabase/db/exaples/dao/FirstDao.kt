package ru.kostry.testdatabase.db.exaples.dao

import androidx.room.*
import ru.kostry.testdatabase.db.exaples.model.FirstEntity

@Dao
interface FirstDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: FirstEntity)

    @Delete
    fun delete(model: FirstEntity)

    @Query("SELECT * FROM FirstEntity")
    fun getAll(): List<FirstEntity>
}