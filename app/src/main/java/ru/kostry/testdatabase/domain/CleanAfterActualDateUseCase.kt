package ru.kostry.testdatabase.domain

import java.util.*

class CleanAfterActualDateUseCase(
    private val repository: UseCasesRepository
) {
    suspend fun execute(date: GregorianCalendar) = repository.cleanAfterDate(date)
}