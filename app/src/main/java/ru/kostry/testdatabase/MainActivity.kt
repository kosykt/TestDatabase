package ru.kostry.testdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.persons.Interval
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainRouteEntity
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(getRandomPersons(100))
            db.instance.trainRouteDao.insert(getRandomRoutes(1000))
            val trainsList = db.instance.trainRouteDao.getNotBusyOrderedByTimeDesc()
            val personsList = db.instance.personDao.getOrderedByTimeAsc()
            startSorting(trainsList, personsList)
        }
    }

    private suspend fun startSorting(
        trainsList: List<TrainRouteEntity>,
        personsList: List<PersonEntity>,
    ) {
        val changedPersons = mutableListOf<PersonEntity>()
        trainsList.forEach { trainEntity ->
            val personEntity: PersonEntity? = personsList
                .filter {
                    checkCanRide(trainEntity, it)
                }
                .minByOrNull {
                    it.workingMillis
                }
                ?.apply {
                    busyTime.add(
                        Interval(
                            trainRoute = trainEntity.routeNumber,
                            start = trainEntity.start,
                            stop = trainEntity.stop,
                        )
                    )
                    refreshWorkingMillis()
                }
            if (personEntity != null) {
                trainEntity.isBusy = true
                trainEntity.personId = personEntity.id
                changedPersons.add(personEntity)
            }
        }
        db.instance.personDao.insert(changedPersons)
        db.instance.trainRouteDao.insert(trainsList)
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

    private fun checkIsBusy(train: TrainRouteEntity, busyTimes: MutableList<Interval>): Boolean {
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
}