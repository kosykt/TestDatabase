package ru.kostry.testdatabase.domain

import kotlinx.coroutines.flow.Flow
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity

interface UseCasesRepository {

    suspend fun addNewPerson(person: PersonEntity)

    suspend fun addNewPerson(persons: List<PersonEntity>)

    suspend fun addNewTrain(train: TrainRouteEntity)

    suspend fun addNewTrain(trains: List<TrainRouteEntity>)

    fun getAllPersons(): Flow<List<PersonEntity>>

    fun getAllTrains(): Flow<List<TrainRouteEntity>>

    suspend fun makeSchedule()
}