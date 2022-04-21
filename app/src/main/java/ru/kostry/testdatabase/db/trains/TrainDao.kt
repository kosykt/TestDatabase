package ru.kostry.testdatabase.db.trains

import androidx.room.*

@Dao
interface TrainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(train: TrainEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(train: List<TrainEntity>)

    @Delete
    fun delete(train: TrainEntity)

    @Delete
    fun delete(train: List<TrainEntity>)

    @Query("SELECT * FROM TrainEntity")
    fun getAll(): List<TrainEntity>
}