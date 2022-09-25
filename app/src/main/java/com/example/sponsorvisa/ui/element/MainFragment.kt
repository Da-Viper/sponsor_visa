package com.example.sponsorvisa.ui.element

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sponsorvisa.R
import com.example.sponsorvisa.adapter.CompanyItemAdapter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        binding.run {
            rvMain.addItemDecoration(CompanyItemSpacing())
            rvMain.adapter = adapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_company, menu)

        val searchItem = menu.findItem(R.id.action_search_bar)?.actionView as SearchView

        Log.i(NAME, "string value got here")
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(NAME, "string value $query")
                query?.let {
                    viewModel.onUiEvent(CompaniesEvent.Search(it))
                }
                return true
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

