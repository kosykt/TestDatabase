package ru.kostry.testdatabase.domain

class GetAllTrainRouteUseCase(
    private val repository: UseCasesRepository
) {

    fun execute() = repository.getAllTrains()
}