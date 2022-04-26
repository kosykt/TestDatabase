package ru.kostry.testdatabase.domain

class GetAllTrainRouteUseCase(
    private val repository: UseCasesRepository
) {

    suspend fun execute() = repository.getAllTrains()
}