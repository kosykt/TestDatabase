package ru.kostry.testdatabase.db.trains

import androidx.room.*

@Dao
interface TrainRouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainRoute: TrainRouteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainRoute: List<TrainRouteEntity>)

    @Delete
    suspend fun delete(trainRoute: TrainRouteEntity)

    @Delete
    suspend fun delete(trainRoute: List<TrainRouteEntity>)

    @Query("SELECT * FROM TrainRouteEntity")
    suspend fun getAll(): List<TrainRouteEntity>

    @Query("SELECT * FROM TrainRouteEntity WHERE isBusy = 0 ORDER BY totalTimeInMillis DESC")
    suspend fun getNotBusyOrderedByTimeDesc(): List<TrainRouteEntity>
}