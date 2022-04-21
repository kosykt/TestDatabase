package ru.kostry.testdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainCheckOutEntity
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
        checkOuts = listOf(
            TrainCheckOutEntity(
                time = GregorianCalendar(2022, Calendar.APRIL, 24, 18, 30)
            )
        ),
        workingHours = 10,
        isBusy = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(listOf(person1, ))
            db.instance.trainDao.insert(listOf(train1, ))
            val trains = db.instance.trainDao.getNotBusyOrderedByHoursDesc().toMutableList()
            val persons = db.instance.personDao.getReadyOrderedByHoursAsc().toMutableList()
//            startSorting(trains, persons)
        }
    }

//    private fun startSorting(trains: MutableList<TrainEntity>, persons: MutableList<PersonEntity>) {
//        trains.forEach { trainEntity ->
//            if (!trainEntity.isBusy) {
//                persons.forEach { personEntity ->
//                    if (personEntity.readyToRide) {
//                        if (checkCanRide(personEntity, trainEntity)) {
//                            personEntity.readyToRide = false
//                            personEntity.hoursWorked += trainEntity.workingHours
//                            personEntity.trainNumber = trainEntity.number
//                            trainEntity.isBusy = true
//                        }
//                    }
//                }
//            }
//        }
//        db.instance.personDao.insert(persons)
//        db.instance.trainDao.insert(trains)
//    }
//
//    private fun checkCanRide(personEntity: PersonEntity, trainEntity: TrainEntity): Boolean {
//        val path = checkCanPersonRideToPathway(personEntity.pathDirections, trainEntity.destination)
//        if (path) {
//            return false
//        }
//        val date = checkPersonDayOffAndTrainDate(personEntity.daysOff, trainEntity.date)
//        return path && date
//    }
//
//    private fun checkCanPersonRideToPathway(
//        pathDirections: List<Map<String, Boolean>>,
//        destination: String,
//    ): Boolean {
//        pathDirections.forEach { map ->
//            if (map.containsKey(destination)) {
//                return map[destination] == true
//            }
//        }
//        return false
//    }
//
//    private fun checkPersonDayOffAndTrainDate(
//        daysOff: List<GregorianCalendar>,
//        date: GregorianCalendar,
//    ): Boolean {
//        daysOff.forEach { gregorianCalendar ->
//            if (gregorianCalendar.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) {
//                return false
//            }
//        }
//        return true
//    }
}