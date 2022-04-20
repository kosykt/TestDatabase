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

    private val train1 = TrainEntity(
        id = 1,
        number = 1,
        destination = "Moscow",
        date = GregorianCalendar(2022, Calendar.APRIL, 20, 18, 30),
        workingHours = 1,
        isBusy = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(person1)
            db.instance.trainDao.insert(train1)
        }
    }
}