package ru.kostry.testdatabase.domain

import ru.kostry.testdatabase.data.db.persons.PersonEntity

class AddNewPersonUseCase(
    private val repository: UseCasesRepository
) {
    suspend fun execute(person: PersonEntity) = repository.addNewPerson(person)

    suspend fun execute(persons: List<PersonEntity>) = repository.addNewPerson(persons)
}