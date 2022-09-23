package com.example.sponsorvisa.viewmodels

import androidx.lifecycle.*
import com.example.sponsorvisa.CompaniesEvent
import com.example.sponsorvisa.SearchCompanyUiState
import com.example.sponsorvisa.data.local.Company
import com.example.sponsorvisa.data.use_cases.CompanyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCompaniesUseCase: CompanyUseCases,
) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SearchCompanyUiState> =
        MutableStateFlow(SearchCompanyUiState.Loading)
    val uiState: StateFlow<SearchCompanyUiState> = _uiState

    init {
        loadDatabase()
    }

    private fun populateDatabase(companies: List<Company>) {
        viewModelScope.launch {
            getCompaniesUseCase.updateCompanies(companies)
        }
    }

    private fun loadDatabase() {
        viewModelScope.launch {
            _uiState.value = SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies())
        }
    }

    fun onEvent(event: CompaniesEvent) {
        viewModelScope.launch {
            when (event) {
                is CompaniesEvent.Load -> {
                    loadDatabase()
                }
                is CompaniesEvent.Search -> {
                    _uiState.value =
                        SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies(event.name))
                }
                is CompaniesEvent.Sort -> {
                    TODO()
                }
            }
        }
    }
}