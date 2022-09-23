package com.example.sponsorvisa

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.databinding.FragmentMainBinding
import com.example.sponsorvisa.viewmodels.SharedViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    private val adapter = CompanyItemAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.adapter = adapter
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_company, menu)

        val searchItem = menu.findItem(R.id.action_search_bar)
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { currentState ->
                    when (currentState) {
                        is SearchCompanyUiState.Loading -> {
                            Log.i("UiState", "Loading")
                        }
                        is SearchCompanyUiState.Success -> {
                            setupSuccessState(currentState)
                        }
                        is SearchCompanyUiState.Error -> {
                            Log.i("UiState", "Error")
                        }
                    }
                }
            }
        }
    }

    private suspend fun setupSuccessState(state: SearchCompanyUiState.Success) {
        state.companies.collect {
            adapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}


@OptIn(FlowPreview::class)
suspend fun <T> Flow<List<T>>.asList() =
    flatMapConcat { it.asFlow() }.toList()

