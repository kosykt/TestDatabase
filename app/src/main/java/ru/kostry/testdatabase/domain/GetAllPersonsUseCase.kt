package ru.kostry.testdatabase.domain

class GetAllPersonsUseCase(
    private val repository: UseCasesRepository
) {
    fun execute() = repository.getAllPersons()
}