package ru.kostry.testdatabase.utils

import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.domain.models.PersonDomainModel
import ru.kostry.testdatabase.domain.models.TrainRouteDomainModel

fun PersonDomainModel.toPersonEntity() = PersonEntity(
    id = this.id,
    firstName = this.firstName,
    secondName = this.secondName,
    thirdName = this.thirdName,
    daysOff = this.daysOff,
    pathDirections = this.pathDirections,
    busyTime = this.busyTime,
    workingMillis = this.workingMillis,
)

fun List<PersonDomainModel>.toListPersonEntity() = this.map { it.toPersonEntity() }

fun PersonEntity.toPersonDomainModel() = PersonDomainModel(
    id = this.id,
    firstName = this.firstName,
    secondName = this.secondName,
    thirdName = this.thirdName,
    daysOff = this.daysOff,
    pathDirections = this.pathDirections,
    busyTime = this.busyTime,
    workingMillis = this.workingMillis,
)

fun List<PersonEntity>.toListPersonDomainModel() = this.map { it.toPersonDomainModel() }

fun TrainRouteDomainModel.toTrainRouteEntity() = TrainRouteEntity(
    id = this.id,
    routeNumber = this.routeNumber,
    destination = this.destination,
    start = this.start,
    stop = this.stop,
    personId = this.personId,
    totalTimeInMillis = this.totalTimeInMillis,
)

fun List<TrainRouteDomainModel>.toListTrainRouteEntity() = this.map { it.toTrainRouteEntity() }

fun TrainRouteEntity.toTrainRouteDomainModel() = TrainRouteDomainModel(
    id = this.id,
    routeNumber = this.routeNumber,
    destination = this.destination,
    start = this.start,
    stop = this.stop,
    personId = this.personId,
    totalTimeInMillis = this.totalTimeInMillis,
)

fun List<TrainRouteEntity>.toListTrainRouteDomainModel() = this.map { it.toTrainRouteDomainModel() }