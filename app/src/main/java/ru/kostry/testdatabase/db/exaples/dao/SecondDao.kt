package ru.kostry.testdatabase.db.exaples.dao

import androidx.room.*
import ru.kostry.testdatabase.db.exaples.model.SecondEntity

@Dao
interface SecondDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: SecondEntity)

    @Delete
    fun delete(model: SecondEntity)

    @Query("SELECT * FROM SecondEntity")
    fun getAll(): List<SecondEntity>
}