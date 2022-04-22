package ru.kostry.testdatabase.db.trains

import androidx.room.*

@Dao
interface TrainRouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainRoute: TrainRouteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainRoute: List<TrainRouteEntity>)

    @Delete
    fun delete(trainRoute: TrainRouteEntity)

    @Delete
    fun delete(trainRoute: List<TrainRouteEntity>)

    @Query("SELECT * FROM TrainRouteEntity")
    fun getAll(): List<TrainRouteEntity>

    @Query("SELECT * FROM TrainRouteEntity ORDER BY totalTimeInMillis DESC")
    fun getOrderedByTimeDesc(): List<TrainRouteEntity>
}