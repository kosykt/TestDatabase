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
            db.instance.personDao.insert(listOf(person1))
            db.instance.trainRouteDao.insert(listOf(train1, train2, train3))
            val trainsList = db.instance.trainRouteDao.getNotBusyOrderedByTimeDesc()
            val personsList = db.instance.personDao.getOrderedByTimeAsc()
            startSorting(trainsList, personsList)
        }
    }


    private fun startSorting(
        trainsList: List<TrainRouteEntity>,
        personsList: List<PersonEntity>,
    ) {
        trainsList.forEach { routeEntity ->
            if (!routeEntity.isBusy) {
                personsList.forEach { personEntity ->
                    if (checkCanRide(routeEntity, personEntity)) {
                        routeEntity.isBusy = true
                        routeEntity.personId = personEntity.id
                        personEntity.busyTime.add(
                            Interval(
                                trainRoute = routeEntity.routeNumber,
                                start = routeEntity.start,
                                stop = routeEntity.stop,
                            )
                        )
                        personEntity.refreshWorkingMillis()
                    }
                }
            }
        }
        db.instance.personDao.insert(personsList)
        db.instance.trainRouteDao.insert(trainsList)
    }

    private fun checkCanRide(routeEntity: TrainRouteEntity, personEntity: PersonEntity): Boolean {
        val route = checkDestination(routeEntity, personEntity)
        val busy = checkIsBusy(routeEntity, personEntity)
        val dayOff = checkDayOff(routeEntity, personEntity)
        return route && busy && dayOff
    }

    private fun checkDayOff(route: TrainRouteEntity, person: PersonEntity): Boolean {
        person.daysOff.forEach { day ->
            if (day.get(Calendar.DAY_OF_YEAR) == route.start.get(Calendar.DAY_OF_YEAR)) {
                return false
            }
        }
        return true
    }

    private fun checkIsBusy(route: TrainRouteEntity, person: PersonEntity): Boolean {
        person.busyTime.forEach { interval ->
            if (interval.start > route.stop || interval.stop < route.start) {
                return true
            }
        }
        return false
    }

    private fun checkDestination(route: TrainRouteEntity, person: PersonEntity): Boolean {
        person.pathDirections.forEach { destination ->
            if (destination.containsKey(route.destination) && destination[route.destination] == true) {
                return true
            }
        }
        return false
    }
}