package ru.kostry.testdatabase.domain

import kotlinx.coroutines.flow.Flow
import ru.kostry.testdatabase.domain.models.PersonDomainModel
import ru.kostry.testdatabase.domain.models.TrainRouteDomainModel
import java.util.*

interface UseCasesRepository {
    suspend fun addNewPerson(person: PersonDomainModel)
    suspend fun addNewPerson(persons: List<PersonDomainModel>)
    suspend fun addNewTrain(train: TrainRouteDomainModel)
    suspend fun addNewTrain(trains: List<TrainRouteDomainModel>)
    fun getAllPersons(): Flow<List<PersonDomainModel>>
    fun getAllTrains(): Flow<List<TrainRouteDomainModel>>
    suspend fun makeSchedule()
    suspend fun cleanAfterDate(date: GregorianCalendar)
}