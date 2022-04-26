package ru.kostry.testdatabase.domain

import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity

class AddNewTrainRouteUseCase(
    private val repository: UseCasesRepository
) {

    suspend fun execute(train: TrainRouteEntity) = repository.addNewTrain(train)

    suspend fun execute(trains: List<TrainRouteEntity>) = repository.addNewTrain(trains)
}