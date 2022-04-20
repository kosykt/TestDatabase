package ru.kostry.testdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainEntity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase
    private val person1 = PersonEntity(
        id = 1,
        readyToRide = true,
        firstName = "Ivan",
        secondName = "Ivanov",
        thirdName = "Ivanovich",
        hoursWorked = 0,
        daysOff = listOf(
            GregorianCalendar(2022, Calendar.APRIL, 20),
            GregorianCalendar(2022, Calendar.APRIL, 22),
            GregorianCalendar(2022, Calendar.APRIL, 24),
        ),
        pathDirections = listOf(
            mapOf("Moscow" to true),
            mapOf("Saint-Petersburg" to true),
        ),
    )
    private val person2 = PersonEntity(
        id = 1,
        readyToRide = true,
        firstName = "Petr",
        secondName = "Petrov",
        thirdName = "Petrovich",
        hoursWorked = 0,
        daysOff = listOf(
            GregorianCalendar(2022, Calendar.APRIL, 22),
            GregorianCalendar(2022, Calendar.APRIL, 24),
            GregorianCalendar(2022, Calendar.APRIL, 26),
        ),
        pathDirections = listOf(
            mapOf("Sochi" to true),
            mapOf("Saint-Petersburg" to false),
        ),
    )

    private val train1 = TrainEntity(
        id = 1,
        number = 1,
        destination = "Moscow",
        date = GregorianCalendar(2022, Calendar.APRIL, 20, 18, 30),
        workingHours = 10,
        isBusy = false
    )

    private val train2 = TrainEntity(
        id = 2,
        number = 2,
        destination = "Saint-Petersburg",
        date = GregorianCalendar(2022, Calendar.APRIL, 24, 18, 30),
        workingHours = 20,
        isBusy = false
    )

    private val train3 = TrainEntity(
        id = 3,
        number = 3,
        destination = "Sochi",
        date = GregorianCalendar(2022, Calendar.APRIL, 26, 18, 30),
        workingHours = 30,
        isBusy = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(person1)
            db.instance.trainDao.insert(listOf(train1, train2, train3))
            val trains = db.instance.trainDao.getNotBusyOrderedByHoursDesc()
            val persons = db.instance.personDao.getReadyOrderedByHoursAsc()
        }
    }
}