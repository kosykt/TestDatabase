package ru.kostry.testdatabase.data.db

import kotlinx.coroutines.flow.Flow
import ru.kostry.testdatabase.data.DatabaseRepository
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.utils.checkCanRide
import ru.kostry.testdatabase.utils.cleanPersonBusyData
import ru.kostry.testdatabase.utils.cleanTrainRouteData
import ru.kostry.testdatabase.utils.models.PersonTimeInterval
import java.util.*

class DatabaseRepositoryImpl(
    private val db: AppDatabase,
) : DatabaseRepository {

    override suspend fun addPerson(personEntity: PersonEntity) {
        db.personDao.insert(personEntity)
    }

    override suspend fun addPerson(personEntity: List<PersonEntity>) {
        db.personDao.insert(personEntity)
    }

    override suspend fun addTrain(trainRouteEntity: TrainRouteEntity) {
        db.trainRouteDao.insert(trainRouteEntity)
    }

    override suspend fun addTrain(trainRouteEntity: List<TrainRouteEntity>) {
        db.trainRouteDao.insert(trainRouteEntity)
    }

    override fun getPersons(): Flow<List<PersonEntity>> {
        return db.personDao.getAllToObserve()
    }

    override fun getTrains(): Flow<List<TrainRouteEntity>> {
        return db.trainRouteDao.getAllToObserve()
    }

    override suspend fun sort() {
        val trainsList: List<TrainRouteEntity> = db.trainRouteDao.getOrderedByTimeDesc()
        val personsList = db.personDao.getOrderedByTimeAsc()

        val changedPersons = mutableListOf<PersonEntity>()
        trainsList.onEach { trainEntity ->
            if (trainEntity.personId == 0) {
                val personEntity: PersonEntity? = personsList
                    .filter {
                        checkCanRide(trainEntity, it)
                    }
                    .minByOrNull {
                        it.workingMillis
                    }
                personEntity?.busyTime?.add(
                    PersonTimeInterval(
                        trainRoute = trainEntity.routeNumber,
                        start = trainEntity.start,
                        stop = trainEntity.stop,
                    )
                )
                personEntity?.refreshWorkingMillis()

                if (personEntity != null) {
                    trainEntity.personId = personEntity.id
                    changedPersons.add(personEntity)
                }
            }
        }
        db.personDao.insert(changedPersons)
        db.trainRouteDao.insert(trainsList)
    }

    override suspend fun cleanAfterDate(date: GregorianCalendar) {
        val trainRoute = cleanTrainRouteData(db.trainRouteDao.getOrderedByTimeDesc(), date)
        val person = cleanPersonBusyData(db.personDao.getOrderedByTimeAsc(), date)
        db.trainRouteDao.insert(trainRoute)
        db.personDao.insert(person)
    }
}