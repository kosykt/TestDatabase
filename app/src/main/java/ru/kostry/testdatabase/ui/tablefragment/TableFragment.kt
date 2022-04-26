package ru.kostry.testdatabase.ui.tablefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.data.UseCasesRepositoryImpl
import ru.kostry.testdatabase.data.db.AppDatabase
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.databinding.FragmentTableBinding
import ru.kostry.testdatabase.domain.*

class TableFragment : Fragment() {

    private val db = AppDatabase.instance
    private val useCasesRepository = UseCasesRepositoryImpl(db)
    private val addPerson = AddNewPersonUseCase(useCasesRepository)
    private val addTrain = AddNewTrainRouteUseCase(useCasesRepository)
    private val getAllPersons = GetAllPersonsUseCase(useCasesRepository)
    private val getAllTrains = GetAllTrainRouteUseCase(useCasesRepository)
    private val makeSchedule = MakeScheduleUseCase(useCasesRepository)

    private var _binding: FragmentTableBinding? = null
    private val binding: FragmentTableBinding
        get() = _binding ?: throw RuntimeException("FragmentTableBinding? = null")

    private val adapter by lazy {
        TableAdapter()
    }

    //переместить во viewModel
    private val observablePersons: StateFlow<List<PersonEntity>> = getAllPersons.execute()
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
        binding.tableRv.adapter = adapter

        binding.makeScheduleBtn.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                makeSchedule.execute()
            }
        }

        lifecycleScope.launchWhenStarted {
            observablePersons
                .collect {
                    adapter.submitList(it)
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