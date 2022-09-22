package com.example.sponsorvisa

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.databinding.FragmentMainBinding
import com.example.sponsorvisa.viewmodels.SharedViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onEvent(CompaniesEvent.Load)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                viewModel.uiState.collect {
//                    Log.i("Recycler view", "setting stuff2")
//                    val c = it.companies.asList()
//                    Log.i("Recycler view", it.companies.asList()[1].toString())
//                    it.apply {
////                        val c = companies.asList()
//                        Log.i("Recycler view", c[1].toString())
//                        _adapter.apply {
//                            submitList(c)
//                        }
//                    }
//                }
//            }
//        }
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

        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_company, menu)

        val searchItem = menu.findItem(R.id.action_search_bar)
        val searchView = searchItem.actionView as SearchView

    }

    private fun setupRecyclerView() {

        val adapter = CompanyItemAdapter()
        binding.rvMain.adapter = adapter
        lifecycleScope.launch {
            viewModel.getCompanies().collect {
                adapter.submitList(it)
            }
        }
        Log.i("Recycler view", "setting stuff")
    }


    private fun setupSearchView() {
        val parent = (requireActivity() as MainActivity)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}


@OptIn(FlowPreview::class)
suspend fun <T> Flow<List<T>>.asList() =
    flatMapConcat { it.asFlow() }.toList()

