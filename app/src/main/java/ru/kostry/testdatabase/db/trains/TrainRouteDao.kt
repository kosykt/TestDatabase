package ru.kostry.testdatabase.db.trains

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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
    fun getAll(): Flow<List<TrainRouteEntity>>

    @Query("SELECT * FROM TrainRouteEntity WHERE personId != 0 ORDER BY totalTimeInMillis DESC")
    suspend fun getNotBusyOrderedByTimeDesc(): List<TrainRouteEntity>

    @Query("SELECT * FROM TrainRouteEntity ORDER BY totalTimeInMillis DESC")
    suspend fun getOrderedByTimeDesc(): List<TrainRouteEntity>
}