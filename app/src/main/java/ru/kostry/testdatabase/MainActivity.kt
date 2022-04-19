package ru.kostry.testdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.persons.DayOffEntityModel
import ru.kostry.testdatabase.db.persons.PathDirectionEntityModel
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainEntity

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase
    private val person1 = PersonEntity(
        readyToRide = true,
        firstName = "Ivan",
        secondName = "Ivanov",
        thirdName = "Ivanovich",
        hoursWorked = 0,
        daysOff = listOf(
            DayOffEntityModel(day = 1, month = 1, year = 2001),
            DayOffEntityModel(day = 2, month = 1, year = 2001),
            DayOffEntityModel(day = 7, month = 1, year = 2001),
            DayOffEntityModel(day = 8, month = 1, year = 2001),
        ),
        pathDirections = listOf(
            PathDirectionEntityModel(destination = "Moscow", permission = true),
            PathDirectionEntityModel(destination = "Saint Petersburg", permission = true),
            PathDirectionEntityModel(destination = "Sochi", permission = true),
            PathDirectionEntityModel(destination = "Omsk", permission = false),
        ),
    )
    private val person2 = PersonEntity(
        readyToRide = true,
        firstName = "Pavel",
        secondName = "Pavlov",
        thirdName = "Pavlovich",
        hoursWorked = 5,
        daysOff = listOf(
            DayOffEntityModel(day = 3, month = 1, year = 2001),
            DayOffEntityModel(day = 4, month = 1, year = 2001),
            DayOffEntityModel(day = 12, month = 1, year = 2001),
            DayOffEntityModel(day = 13, month = 1, year = 2001),
        ),
        pathDirections = listOf(
            PathDirectionEntityModel(destination = "Moscow", permission = false),
            PathDirectionEntityModel(destination = "Saint Petersburg", permission = true),
            PathDirectionEntityModel(destination = "Sochi", permission = true),
            PathDirectionEntityModel(destination = "Omsk", permission = true),
        ),
    )
    private val person3 = PersonEntity(
        readyToRide = false,
        firstName = "Petr",
        secondName = "Petrov",
        thirdName = "Petrovich",
        hoursWorked = 10,
        daysOff = listOf(
            DayOffEntityModel(day = 5, month = 1, year = 2001),
            DayOffEntityModel(day = 7, month = 1, year = 2001),
            DayOffEntityModel(day = 13, month = 1, year = 2001),
            DayOffEntityModel(day = 15, month = 1, year = 2001),
        ),
        pathDirections = listOf(
            PathDirectionEntityModel(destination = "Moscow", permission = true),
            PathDirectionEntityModel(destination = "Saint Petersburg", permission = false),
            PathDirectionEntityModel(destination = "Sochi", permission = true),
            PathDirectionEntityModel(destination = "Omsk", permission = true),
        ),
    )

    private val train1 = TrainEntity(
        number = 1,
        destination = "Sochi",
        workingHours = 1,
        isBusy = false
    )
    private val train2 = TrainEntity(
        number = 2,
        destination = "Saint Petersburg",
        workingHours = 5,
        isBusy = false
    )
    private val train3 = TrainEntity(
        number = 3,
        destination = "Moscow",
        workingHours = 10,
        isBusy = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO) {

            db.instance.personDao.insert(listOf(person1, person2, person3))
            db.instance.trainDao.insert(listOf(train1, train2, train3))
            val responsePersons = db.instance.personDao.getSortedPersons()
            val responseTrains = db.instance.trainDao.getSortedTrains()
            distribution(responsePersons, responseTrains)
        }
    }

    private fun distribution(persons: List<PersonEntity>, trains: List<TrainEntity>) {
        for (i in persons.indices) {
            persons[i].pathDirections.forEach {
                if (it.destination == trains[i].destination && it.permission) {
                    persons[i].readyToRide = false
                    persons[i].trainNumber += trains[i].number
                    persons[i].hoursWorked += trains[i].workingHours
                    trains[i].isBusy = true
                }
            }
        }
        db.instance.personDao.insert(persons)
        db.instance.trainDao.insert(trains)
    }
}