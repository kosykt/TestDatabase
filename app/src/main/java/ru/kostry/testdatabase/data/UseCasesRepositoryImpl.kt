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
    private val db: AppDatabase,
) : UseCasesRepository {

    override suspend fun addNewPerson(person: PersonDomainModel) {
        db.personDao.insert(person.toPersonEntity())
    }

    override suspend fun addNewPerson(persons: List<PersonDomainModel>) {
        db.personDao.insert(persons.toListPersonEntity())
    }

    override suspend fun addNewTrain(train: TrainRouteDomainModel) {
        db.trainRouteDao.insert(train.toTrainRouteEntity())
    }

    override suspend fun addNewTrain(trains: List<TrainRouteDomainModel>) {
        db.trainRouteDao.insert(trains.toListTrainRouteEntity())
    }

    override fun getAllPersons(): Flow<List<PersonDomainModel>> {
        return db.personDao.getAllToObserve().map { it.toListPersonDomainModel() }
    }

    override fun getAllTrains(): Flow<List<TrainRouteDomainModel>> {
        return db.trainRouteDao.getAllToObserve().map { it.toListTrainRouteDomainModel() }
    }

    override suspend fun makeSchedule() {
        val trainsList = db.trainRouteDao.getOrderedByTimeDesc()
        val personsList = db.personDao.getOrderedByTimeAsc()

        val changedPersons = mutableListOf<PersonEntity>()
        trainsList.forEach { trainEntity ->
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
        val t = cleanTrainRouteData(db.trainRouteDao.getOrderedByTimeDesc(), date)
        val p = cleanPersonBusyData(db.personDao.getOrderedByTimeAsc(), date)
        db.trainRouteDao.insert(t)
        db.personDao.insert(p)
    }

    private fun checkCanRide(routeEntity: TrainRouteEntity, personEntity: PersonEntity): Boolean {
        val route = checkDestination(routeEntity.destination, personEntity.pathDirections)
        val busy = checkIsBusy(routeEntity, personEntity.busyTime)
        val dayOff = checkDayOff(routeEntity.start, personEntity.daysOff)
        return route && busy && dayOff
    }

    private fun checkDayOff(
        trainStartTime: GregorianCalendar,
        daysOff: List<GregorianCalendar>,
    ): Boolean {
        daysOff.forEach { day ->
            if (day.get(Calendar.DAY_OF_YEAR) == trainStartTime.get(Calendar.DAY_OF_YEAR)) {
                return false
            }
        }
        return true
    }

    private fun checkIsBusy(
        train: TrainRouteEntity,
        busyTimes: MutableList<PersonTimeInterval>,
    ): Boolean {
        if (busyTimes.isEmpty()) {
            return true
        }
        busyTimes.forEach { interval ->
            if (interval.start > train.stop || interval.stop < train.start) {
                return true
            }
        }
        return false
    }

    private fun checkDestination(
        destination: String,
        pathDirections: List<Map<String, Boolean>>,
    ): Boolean {
        pathDirections.forEach { path ->
            if (path.containsKey(destination) && path[destination] == true) {
                return true
            }
        }
        return false
    }

    private fun cleanTrainRouteData(
        trainsList: List<TrainRouteEntity>,
        actualDate: GregorianCalendar,
    ): List<TrainRouteEntity> {
        trainsList.forEach { route ->
            if (route.start.get(Calendar.DAY_OF_YEAR) > actualDate.get(Calendar.DAY_OF_YEAR)) {
                route.personId = 0
            }
        }
        return trainsList
    }

    private fun cleanPersonBusyData(
        personsList: List<PersonEntity>,
        actualDate: GregorianCalendar,
    ): List<PersonEntity> {
        personsList.forEach { person ->
            person.busyTime.removeIf { interval ->
                interval.start.get(Calendar.DAY_OF_YEAR) > actualDate.get(Calendar.DAY_OF_YEAR)
            }
            person.refreshWorkingMillis()
        }
        return personsList
    }
}