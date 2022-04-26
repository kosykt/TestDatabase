package ru.kostry.testdatabase.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kostry.testdatabase.data.db.AppDatabase
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.domain.UseCasesRepository
import ru.kostry.testdatabase.domain.models.PersonDomainModel
import ru.kostry.testdatabase.domain.models.TrainRouteDomainModel
import ru.kostry.testdatabase.utils.*
import ru.kostry.testdatabase.utils.models.PersonTimeInterval
import java.util.*

class UseCasesRepositoryImpl(
    private val databaseRepository: DatabaseRepository
) : UseCasesRepository {

    override suspend fun addNewPerson(person: PersonDomainModel) {
        databaseRepository.addPerson(person.toPersonEntity())
    }

    override suspend fun addNewPerson(persons: List<PersonDomainModel>) {
        databaseRepository.addPerson(persons.toListPersonEntity())
    }

    override suspend fun addNewTrain(train: TrainRouteDomainModel) {
        databaseRepository.addTrain(train.toTrainRouteEntity())
    }

    override suspend fun addNewTrain(trains: List<TrainRouteDomainModel>) {
        databaseRepository.addTrain(trains.toListTrainRouteEntity())
    }

    override fun getAllPersons(): Flow<List<PersonDomainModel>> {
        return databaseRepository.getPersons().map { it.toListPersonDomainModel() }
    }

    override fun getAllTrains(): Flow<List<TrainRouteDomainModel>> {
        return databaseRepository.getTrains().map { it.toListTrainRouteDomainModel() }
    }

    override suspend fun makeSchedule() {
        databaseRepository.sort()
    }

    override suspend fun cleanAfterDate(date: GregorianCalendar) {
        databaseRepository.cleanAfterDate(date)
    }
}