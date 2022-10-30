package com.example.sponsorvisa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.sponsorvisa.ui.state.CompanyUiState
import com.example.sponsorvisa.domain.use_cases.CompanyUseCases
import com.example.sponsorvisa.domain.utils.CompanySort
import com.example.sponsorvisa.ui.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val companyUseCase: CompanyUseCases,
) :
    ViewModel() {

    private val _uiSearchState = MutableStateFlow(SearchUiState())

    private val _uiState: MutableStateFlow<CompanyUiState> =
        MutableStateFlow(CompanyUiState.Loading)
    val uiState: StateFlow<CompanyUiState> = _uiState.asStateFlow()
    val searchFunc = this::searchCompanies

    init {
        Log.d(NAME, "In the view model")
        loadDatabase()
    }

    val updateFilter: (CompanySort) -> Unit = { filterType ->
        _uiSearchState.update { currentState ->
            currentState.copy(
                filter = filterType
            )
        }
        Log.d(NAME, "Filter has been updated to ${_uiSearchState.value.filter.sortType}")
    }

    private fun searchCompanies(searchString: String = INITIAL_QUERY) {
        viewModelScope.launch(Dispatchers.IO) {

            _uiState.value = CompanyUiState.Success(
                companyUseCase.getCompanies(searchString, _uiSearchState.value.filter)
            )
            Log.i(NAME, "Searched for new companies successfully")
        }
    }


    private fun loadDatabase() {
        searchCompanies()
    }

    companion object {
        const val NAME = "SharedViewModel"
        const val INITIAL_QUERY = ""
    }
}