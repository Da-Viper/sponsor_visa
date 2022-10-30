package com.example.sponsorvisa.ui.element

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.example.sponsorvisa.R
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.databinding.BottomToolbarBinding
import com.example.sponsorvisa.databinding.FragmentMainBinding
import com.example.sponsorvisa.domain.utils.CompanySort
import com.example.sponsorvisa.domain.utils.SortType
import com.example.sponsorvisa.ui.state.CompanyUiState
import com.example.sponsorvisa.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    private val adapter = CompanyItemAdapter()
    private var asc: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is SearchCompanyUiState.Success -> {
                            it.companies.collectLatest(adapter::submitData)
                        }
                        else -> {}
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(binding.toolBar)
        setupButtonclick()
        binding.run {
            rvMain.addItemDecoration(CompanyItemSpacing())
            rvMain.adapter = adapter
        }
    }


    private fun setupActionBar(toolBar: BottomToolbarBinding) {
        val searchView = toolBar.searchView
        toolBar.btnFilterSort.setImageResource(R.drawable.ic_action_filtersort)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(NAME, "string value $query")
                query?.let {
                    viewModel.onUiEvent(CompaniesEvent.Search(it))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupButtonclick() {
        binding.toolBar.btnFilterSort.setOnClickListener {
            Log.i(NAME, "asc value : $asc")
            asc = !asc
            val sortType = when (asc) {
                true -> CompaniesEvent.Sort(CompanySort.Name(SortType.Ascending))
                false -> CompaniesEvent.Sort(CompanySort.Name(SortType.Descending))
            }
            Log.i(NAME, "asc value : $asc")
            viewModel.onUiEvent(sortType)
        }

    }

    companion object {
        const val NAME = "MainFragment"
        fun newInstance() = MainFragment()
    }
}

private fun FragmentMainBinding.bindState(
    uiState: SearchCompanyUiState, paging: PagingData<Company>,
    uiAction: CompaniesEvent
) {


}

