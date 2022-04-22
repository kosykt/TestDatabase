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
        busyTime = listOf(
            Interval(
                trainRoute = "456B",
                start = GregorianCalendar(2022, Calendar.APRIL, 20, 8, 30),
                stop = GregorianCalendar(2022, Calendar.APRIL, 20, 9, 30),
            )
        )
    )

    private val train1 = TrainRouteEntity(
        id = 1,
        routeNumber = "123A",
        destination = "Moscow",
        start = GregorianCalendar(2022, Calendar.APRIL, 20, 12, 0),
        stop = GregorianCalendar(2022, Calendar.APRIL, 20, 13, 0),
    )

    private val train2 = TrainRouteEntity(
        id = 2,
        routeNumber = "123A",
        destination = "Saint-Petersburg",
        start = GregorianCalendar(2022, Calendar.APRIL, 21, 12, 0),
        stop = GregorianCalendar(2022, Calendar.APRIL, 21, 14, 0),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(listOf(person1))
            db.instance.trainRouteDao.insert(listOf(train1, train2))
            val trainsList = db.instance.trainRouteDao.getOrderedByTimeDesc()
            val personsList = db.instance.personDao.getOrderedByTimeAsc()
            startSorting(trainsList, personsList)
        }
    }


    private fun startSorting(
        trainsList: List<TrainRouteEntity>,
        personsList: List<PersonEntity>,
    ) {

    }

}