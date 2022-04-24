package ru.kostry.testdatabase.db.persons

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: PersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: List<PersonEntity>)

    @Delete
    suspend fun delete(model: PersonEntity)

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): Flow<List<PersonEntity>>

    @Query("SELECT * FROM PersonEntity ORDER BY workingMillis ASC")
    suspend fun getOrderedByTimeAsc(): List<PersonEntity>
}