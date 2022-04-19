package ru.kostry.testdatabase.db.dao

import androidx.room.*
import ru.kostry.testdatabase.db.model.ThirdEntity

@Dao
interface ThirdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: ThirdEntity)

    @Delete
    fun delete(model: ThirdEntity)

    @Query("SELECT * FROM ThirdEntity")
    fun getAll(): List<ThirdEntity>
}