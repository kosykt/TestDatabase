package ru.kostry.testdatabase.domain

import ru.kostry.testdatabase.domain.models.PersonDomainModel

class AddNewPersonUseCase(
    private val repository: UseCasesRepository,
) {
    suspend fun execute(person: PersonDomainModel) = repository.addNewPerson(person)
    suspend fun execute(persons: List<PersonDomainModel>) = repository.addNewPerson(persons)
}