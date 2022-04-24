package ru.kostry.testdatabase

import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainRouteEntity
import java.util.*

fun getRandomPersons(int: Int): List<PersonEntity> {
    val list = mutableListOf<PersonEntity>()
    for (i in 0 until int) {
        list.add(getPerson(i))
    }
    return list
}

fun getPerson(i: Int): PersonEntity {
    return PersonEntity(
        firstName = "Person $i",
        secondName = "Person $i",
        thirdName = "Person $i",
        daysOff = getDaysOff(),
        pathDirections = getDirections()
    )
}

fun getDirections(): List<Map<String, Boolean>> {
    val list = mutableListOf<Map<String, Boolean>>()
    val b = listOf(true, false).shuffled()
    when ((0..7).random()) {
        0 -> {
            list.add(mapOf("Moscow" to true))
        }
        1 -> {
            list.add(mapOf("Saint-Petersburg" to true))
        }
        3 -> {
            list.add(mapOf("Sochi" to true))
        }
        4 -> {
            list.add(mapOf("Moscow" to b[0]))
            list.add(mapOf("Sochi" to b[1]))
        }
        5 -> {
            list.add(mapOf("Saint-Petersburg" to b[1]))
            list.add(mapOf("Sochi" to b[0]))
        }
        6 -> {
            list.add(mapOf("Saint-Petersburg" to b[1]))
            list.add(mapOf("Sochi" to b[0]))
            list.add(mapOf("Moscow" to b[1]))
        }
        else -> {
            list.add(mapOf("Saint-Petersburg" to true))
            list.add(mapOf("Sochi" to true))
            list.add(mapOf("Moscow" to true))
        }
    }
    return list
}


fun getDaysOff(): MutableList<GregorianCalendar> {
    val list = mutableListOf<GregorianCalendar>()
    for (i in 0..(0..6).random()) {
        list.add(GregorianCalendar(2022, (0..11).random(), (0..30).random()))
    }
    return list
}

fun getRandomRoutes(int: Int): MutableList<TrainRouteEntity> {
    val list = mutableListOf<TrainRouteEntity>()
    for (i in 0 until int) {
        list.add(getTrain())
    }
    return list
}

fun getTrain(): TrainRouteEntity {
    val date = getRouteDate()
    return TrainRouteEntity(
        routeNumber = "${(1000..9999).random()}",
        destination = getCity(),
        start = date[0],
        stop = date[1],
    )
}

fun getRouteDate(): List<GregorianCalendar> {
    val month = (0..11).random()
    val day = (0..31).random()
    val hour = (0..24).random()
    val minute = (0..60).random()
    val start = GregorianCalendar(2022, month, day, hour, minute)
    val stop = GregorianCalendar(2022, month, day, hour + (1..5).random(), minute)
    return listOf(start, stop)
}

fun getCity(): String {
    return when ((0..2).random()) {
        0 -> "Moscow"
        1 -> "Saint-Petersburg"
        else -> "Sochi"
    }
}


