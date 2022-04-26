package ru.kostry.testdatabase.domain

import ru.kostry.testdatabase.domain.models.TrainRouteDomainModel

class AddNewTrainRouteUseCase(
    private val repository: UseCasesRepository,
) {
    suspend fun execute(train: TrainRouteDomainModel) = repository.addNewTrain(train)
    suspend fun execute(trains: List<TrainRouteDomainModel>) = repository.addNewTrain(trains)
}