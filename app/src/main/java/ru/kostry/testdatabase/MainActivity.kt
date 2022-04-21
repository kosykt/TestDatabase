package ru.kostry.testdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.TimeInterval
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainEntity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase
    private val person1 = PersonEntity(
        id = 1,
        firstName = "Ivan",
        secondName = "Ivanov",
        thirdName = "Ivanovich",
        daysOff = listOf(
            GregorianCalendar(2022, Calendar.APRIL, 20),
        ),
        pathDirections = listOf(
            mapOf("Moscow" to true),
            mapOf("Saint-Petersburg" to true),
        ),
    )

    private val train1 = TrainEntity(
        id = 1,
        routeNumber = "123A",
        destination = "Moscow",
        time = TimeInterval(
            GregorianCalendar(2022, Calendar.APRIL, 20, 8,30),
            GregorianCalendar(2022, Calendar.APRIL, 20, 17, 30),
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.personDao.insert(listOf(person1))
            db.instance.trainDao.insert(listOf(train1))
            val trains = db.instance.trainDao.getAll().toMutableList()
            val persons = db.instance.personDao.getAll().toMutableList()
//            startSorting(trains, persons)
        }
    }


//    private fun startSorting(trains: MutableList<TrainEntity>, persons: MutableList<PersonEntity>) {
//        trains.forEach { trainEntity ->
//            trainEntity.checkOuts.forEach { trainCheckOut ->
//                persons.forEach { personEntity ->
//                    if (checkCanRide(personEntity, trainCheckOut)) {
//
//                    }
//                }
//            }
//        }
//
//    }
//
//    private fun checkCanRide(
//        person: PersonEntity,
//        checkOut: TrainCheckOutEntity,
//    ): Boolean {
//        val path = checkCanPersonRideToPathway(person.pathDirections, checkOut.destination)
//        if (!path) {
//            return false
//        }
//        val date = checkPersonDayOffAndTrainDate(person.daysOff, checkOut.time)
//        return path && date
//    }
//
//    private fun checkCanPersonRideToPathway(
//        pathDirections: List<Map<String, Boolean>>,
//        destination: String,
//    ): Boolean {
//        pathDirections.forEach { map ->
//            if ((map.containsKey(destination)) && map[destination] == true) {
//                return true
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