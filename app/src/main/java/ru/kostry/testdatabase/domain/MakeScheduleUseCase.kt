package ru.kostry.testdatabase.domain

class MakeScheduleUseCase(
    private val repository: UseCasesRepository
) {

    suspend fun execute() = repository.makeSchedule()
}