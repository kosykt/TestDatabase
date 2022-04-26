package ru.kostry.testdatabase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.R
import ru.kostry.testdatabase.data.UseCasesRepositoryImpl
import ru.kostry.testdatabase.data.db.AppDatabase
import ru.kostry.testdatabase.data.db.persons.Interval
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.domain.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase.instance
    private val useCasesRepository = UseCasesRepositoryImpl(db)
    private val addPerson = AddNewPersonUseCase(useCasesRepository)
    private val addTrain = AddNewTrainRouteUseCase(useCasesRepository)
    private val getAllPersons = GetAllPersonsUseCase(useCasesRepository)
    private val getAllTrains = GetAllTrainRouteUseCase(useCasesRepository)
    private val makeSchedule = MakeScheduleUseCase(useCasesRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {

        }
    }
}