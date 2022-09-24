package com.example.sponsorvisa.ui.element

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sponsorvisa.R
import com.example.sponsorvisa.ui.state.SearchCompanyUiState
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.databinding.FragmentMainBinding
import com.example.sponsorvisa.ui.state.CompaniesEvent
import com.example.sponsorvisa.viewmodels.SharedViewModel

class MainFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    private val adapter = CompanyItemAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.uiState.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.adapter = adapter
//        setupObservers()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_company, menu)

        val searchItem = menu.findItem(R.id.action_search_bar)?.actionView as SearchView
//        val searchItem = menu.findItem(R.id.action_search_bar) as SearchView

        Log.i(NAME, "string value got here")
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(NAME, "string value $query")
                query?.let {
                    viewModel.onEvent(CompaniesEvent.Search(it))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupObservers() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect { currentState ->
//                    when (currentState) {
//                        is SearchCompanyUiState.Loading -> {
//                            Log.i("UiState", "UISTATE Loading")
//                        }
//                        is SearchCompanyUiState.Success -> {
//                            Log.i("UiState", "UISTATE Success")
//                            setupSuccessState(currentState)
//                        }
//                        is SearchCompanyUiState.Error -> {
//                            Log.i("UiState", "Error")
//                        }
//                    }
//                }
//            }
//        }
    }

    private suspend fun setupSuccessState(state: SearchCompanyUiState.Success) {
        state.companies.collect {
            Log.i("the list is ", "the list is $it")
            adapter.submitList(it)
        }

    }

    companion object {
        const val NAME = "MainFragment"
        fun newInstance() = MainFragment()
    }
}

