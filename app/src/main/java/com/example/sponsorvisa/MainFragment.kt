package com.example.sponsorvisa

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sponsorvisa.adapter.CompanyItemAdapter
import com.example.sponsorvisa.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

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

        val searchItem =menu.findItem(R.id.action_search_bar)
        val searchView = searchItem.actionView as SearchView

    }
    private fun setupRecyclerView() {

        Log.i("Recycler view", "setting stuff")
        viewModel.localData.observe(viewLifecycleOwner) {
            binding.rvMain.adapter = CompanyItemAdapter().apply {
                Log.i("Recycler view", it[0].city.toString())
                submitList(it)
            }
        }
    }



    private fun setupSearchView() {
        val parent = (requireActivity() as MainActivity)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}