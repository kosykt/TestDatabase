package ru.kostry.testdatabase.ui.tablefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.kostry.testdatabase.data.UseCasesRepositoryImpl
import ru.kostry.testdatabase.data.db.AppDatabase
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.databinding.FragmentTableBinding
import ru.kostry.testdatabase.domain.*
import ru.kostry.testdatabase.utils.getRandomPersons
import ru.kostry.testdatabase.utils.getRandomRoutes
import java.util.*

class TableFragment : Fragment() {

    private val db = AppDatabase.instance
    private val useCasesRepository = UseCasesRepositoryImpl(db)
    private val addPerson = AddNewPersonUseCase(useCasesRepository)
    private val addTrain = AddNewTrainRouteUseCase(useCasesRepository)
    private val getAllPersons = GetAllPersonsUseCase(useCasesRepository)
    private val getAllTrains = GetAllTrainRouteUseCase(useCasesRepository)
    private val makeSchedule = MakeScheduleUseCase(useCasesRepository)
    private val cleanAfter = CleanAfterActualDateUseCase(useCasesRepository)

    private var _binding: FragmentTableBinding? = null
    private val binding: FragmentTableBinding
        get() = _binding ?: throw RuntimeException("FragmentTableBinding? = null")

    private val adapterPersons by lazy {
        TablePersonsAdapter()
    }

    private val adapterTrains by lazy {
        TableTrainsAdapter()
    }

    //переместить во viewModel
    private val observablePersons: StateFlow<List<PersonEntity>> = getAllPersons.execute()
        .stateIn(
            scope = lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    private val observableTrains: StateFlow<List<TrainRouteEntity>> = getAllTrains.execute()
        .stateIn(
            scope = lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTableBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tablePersons.adapter = adapterPersons
        binding.tableTrains.adapter = adapterTrains

        initClickListeners()
        initObservables()
    }

    private fun initObservables() {
        lifecycleScope.launchWhenStarted {
            observablePersons.collect {
                adapterPersons.submitList(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            observableTrains.collect{
                adapterTrains.submitList(it)
            }
        }
    }

    private fun initClickListeners() {
        binding.makeScheduleBtn.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                makeSchedule.execute()
            }
        }
        binding.addPerson.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                addPerson.execute(getRandomPersons(1))
            }
        }
        binding.addTrain.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                addTrain.execute(getRandomRoutes(1))
            }
        }
        binding.cleanAfter.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                cleanAfter.execute(GregorianCalendar())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = TableFragment()
    }
}