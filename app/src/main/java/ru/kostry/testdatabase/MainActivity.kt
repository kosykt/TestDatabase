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
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase
    private val person1 = PersonEntity(
        id = 1,
        firstName = "Ivan",
        secondName = "Ivanov",
        thirdName = "Ivanovich",
        daysOff = listOf(
            GregorianCalendar(2022, Calendar.APRIL, 22),
        ),
        pathDirections = listOf(
            mapOf("Moscow" to true),
            mapOf("Saint-Petersburg" to true),
        ),
        busyTime = mutableListOf(
            Interval(
                trainRoute = "456B",
                start = GregorianCalendar(2022, Calendar.APRIL, 20, 12, 0),
                stop = GregorianCalendar(2022, Calendar.APRIL, 20, 13, 0),
            )
        )
    )

    private val person2 = PersonEntity(
        id = 2,
        firstName = "Petr",
        secondName = "Petrov",
        thirdName = "Petrovich",
        daysOff = listOf(
            GregorianCalendar(2022, Calendar.APRIL, 22),
        ),
        pathDirections = listOf(
            mapOf("Moscow" to true),
            mapOf("Saint-Petersburg" to true),
        ),
        busyTime = mutableListOf(
            Interval(
                trainRoute = "11111B",
                start = GregorianCalendar(2022, Calendar.APRIL, 20, 12, 0),
                stop = GregorianCalendar(2022, Calendar.APRIL, 25, 13, 0),
            )
        )
    )

    private val train1 = TrainRouteEntity(
        id = 1,
        routeNumber = "123A",
        destination = "Moscow",
        start = GregorianCalendar(2022, Calendar.APRIL, 20, 13, 30),
        stop = GregorianCalendar(2022, Calendar.APRIL, 20, 14, 0),
    )

    private val train2 = TrainRouteEntity(
        id = 2,
        routeNumber = "789A",
        destination = "Saint-Petersburg",
        start = GregorianCalendar(2022, Calendar.APRIL, 21, 12, 0),
        stop = GregorianCalendar(2022, Calendar.APRIL, 21, 14, 0),
    )

    private val train3 = TrainRouteEntity(
        id = 3,
        routeNumber = "456B",
        destination = "Saint-Petersburg",
        start = GregorianCalendar(2022, Calendar.APRIL, 20, 12, 0),
        stop = GregorianCalendar(2022, Calendar.APRIL, 20, 13, 0),
        personId = 1,
        isBusy = true
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(listOf(person1, person2))
            db.instance.trainRouteDao.insert(listOf(train1, train2, train3))
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
            val person = getPersonWithMinTime(personsList)
            if (person != null && checkCanRide(trainEntity, person)) {
                trainEntity.isBusy = true
                trainEntity.personId = person.id
                person.busyTime.add(
                    Interval(
                        trainRoute = trainEntity.routeNumber,
                        start = trainEntity.start,
                        stop = trainEntity.stop,
                    )
                )
                person.refreshWorkingMillis()
                changedPersons.add(person)
            }
        }
        db.instance.personDao.insert(changedPersons)
        db.instance.trainRouteDao.insert(trainsList)
    }

    private fun getPersonWithMinTime(personsList: List<PersonEntity>): PersonEntity? {
        return personsList.minByOrNull { it.workingMillis }
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