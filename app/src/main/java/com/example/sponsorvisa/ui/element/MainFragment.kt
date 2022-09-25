package com.example.sponsorvisa.ui.element

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sponsorvisa.R
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.databinding.BottomToolbarBinding
import com.example.sponsorvisa.databinding.FragmentMainBinding
import com.example.sponsorvisa.ui.state.CompaniesEvent
import com.example.sponsorvisa.viewmodels.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    private val adapter = CompanyItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                Log.i("SharedViewModel", "paging value: $it")
                adapter.submitData(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(binding.toolBar)
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

    companion object {
        const val NAME = "MainFragment"
        fun newInstance() = MainFragment()
    }
}

