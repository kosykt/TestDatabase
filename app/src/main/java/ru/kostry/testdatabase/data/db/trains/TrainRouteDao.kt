package ru.kostry.testdatabase.data.db.trains

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

    @Query("SELECT * FROM TrainRouteEntity")
    fun getAllToObserve(): Flow<List<TrainRouteEntity>>

    @Query("SELECT * FROM TrainRouteEntity ORDER BY totalTimeInMillis DESC")
    suspend fun getOrderedByTimeDesc(): List<TrainRouteEntity>
}