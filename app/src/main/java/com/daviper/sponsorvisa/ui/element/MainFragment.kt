package com.daviper.sponsorvisa.ui.element

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
import com.daviper.sponsorvisa.adapter.CompanyItemAdapter
import com.daviper.sponsorvisa.databinding.BottomToolbarBinding
import com.daviper.sponsorvisa.databinding.FragmentMainBinding
import com.daviper.sponsorvisa.domain.utils.CompanySort
import com.daviper.sponsorvisa.domain.utils.SortType
import com.daviper.sponsorvisa.ui.state.CompanyUiState
import com.daviper.sponsorvisa.viewmodels.SharedViewModel
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

    //    private val bottomSheet = ItemListDialogFragment.newInstance(4)
    private var asc: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        setupListeners(viewModel.updateFilter)
        setupActionBar(binding.toolBar, viewModel.searchFunc)
        binding.run {
            rvMain.addItemDecoration(CompanyItemSpacing())
            rvMain.adapter = adapter
        }
    }

    private fun initObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        // show the recycler view
                        // hide the progress bar
                        is CompanyUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvMain.visibility = View.VISIBLE
                            it.companies.collectLatest(adapter::submitData)
                        }
                        // hide the recycler view
                        // show the progress bar
                        else -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvMain.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupActionBar(toolBar: BottomToolbarBinding, searchFunc: (String) -> Unit) {
        val searchView = toolBar.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(NAME, "string value $query")
                query?.let {
                    searchFunc(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupListeners(setFilter: (CompanySort) -> Unit) {
        binding.toolBar.btnFilterSort.setOnClickListener {
            Log.d(NAME, "Clicked button")
            asc = when (asc) {
                true -> {
                    setFilter(CompanySort.Name(SortType.Ascending))
                    !asc
                }
                false -> {
                    setFilter(CompanySort.Name(SortType.Descending))
                    !asc
                }
            }
        }

    }

    companion object {
        const val NAME = "MainFragment"
    }
}

