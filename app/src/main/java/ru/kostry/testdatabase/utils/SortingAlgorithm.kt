package ru.kostry.testdatabase.utils

import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.utils.models.PersonTimeInterval
import java.util.*

fun cleanTrainRouteData(
    trainsList: List<TrainRouteEntity>,
    actualDate: GregorianCalendar,
): List<TrainRouteEntity> {
    trainsList.forEach { route ->
        if (route.start.get(Calendar.DAY_OF_YEAR) > actualDate.get(Calendar.DAY_OF_YEAR)) {
            route.personId = 0
        }
    }
    return trainsList
}

fun cleanPersonBusyData(
    personsList: List<PersonEntity>,
    actualDate: GregorianCalendar,
): List<PersonEntity> {
    personsList.forEach { person ->
        person.busyTime.removeIf { interval ->
            interval.start.get(Calendar.DAY_OF_YEAR) > actualDate.get(Calendar.DAY_OF_YEAR)
        }
        person.refreshWorkingMillis()
    }
    return personsList
}

fun checkCanRide(routeEntity: TrainRouteEntity, personEntity: PersonEntity): Boolean {
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

private fun checkIsBusy(
    train: TrainRouteEntity,
    busyTimes: MutableList<PersonTimeInterval>,
): Boolean {
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