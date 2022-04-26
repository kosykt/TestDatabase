package ru.kostry.testdatabase.data

import kotlinx.coroutines.flow.Flow
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import java.util.*

interface DatabaseRepository {

    suspend fun addPerson(personEntity: PersonEntity)
    suspend fun addPerson(personEntity: List<PersonEntity>)
    suspend fun addTrain(trainRouteEntity: TrainRouteEntity)
    suspend fun addTrain(trainRouteEntity: List<TrainRouteEntity>)
    fun getPersons(): Flow<List<PersonEntity>>
    fun getTrains(): Flow<List<TrainRouteEntity>>
    suspend fun sort()
    suspend fun cleanAfterDate(date: GregorianCalendar)
}