package ru.kostry.testdatabase.domain

class GetAllPersonsUseCase(
    private val repository: UseCasesRepository
) {

    suspend fun getAllPersons() = repository.getAllPersons()
}