package com.example.sponsorvisa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.sponsorvisa.ui.state.CompaniesEvent
import com.example.sponsorvisa.ui.state.SearchCompanyUiState
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.domain.use_cases.CompanyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
//    val uiState: StateFlow<SearchCompanyUiState> = _uiState
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: LiveData<List<Company>> = _uiState.flatMapLatest { zone ->
        return@flatMapLatest when (zone) {
            is SearchCompanyUiState.Loading -> flow{emit(emptyList())}
            is SearchCompanyUiState.Success -> zone.companies
            is SearchCompanyUiState.Error -> flow { emit(emptyList()) }
    //is SearchCompanyUiState.Error -> {zone}
        }
    }.asLiveData()

    init {
        onEvent(CompaniesEvent.Load)
    }

    private fun populateDatabase(companies: List<Company>) {
        viewModelScope.launch {
            getCompaniesUseCase.updateCompanies(companies)
        }
    }

    private fun loadDatabase() {
        viewModelScope.launch {
            _uiState.emit(SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies()))
//            delay(5000)
//            Log.i(NAME, "REloading database")
//            _uiState.emit(SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies("lon")))
//            Log.i(NAME, "done database")
        }
    }

    fun onEvent(event: CompaniesEvent) {
        viewModelScope.launch {
            when (event) {
                is CompaniesEvent.Load -> {
                    loadDatabase()
                    Log.i(NAME, "loaded database")
                }
                is CompaniesEvent.Search -> {
                    Log.i(NAME, "search: ${event.name}")
                    _uiState.emit(SearchCompanyUiState.Loading)
                    _uiState.emit(
                        SearchCompanyUiState.Success(
                            getCompaniesUseCase.getCompanies(
                                event.name
                            )
                        )
                    )
//                    val ss = getCompaniesUseCase.getCompanies(event.name)
                    Log.i(NAME, "search2 : ${event.name}")
//                    _uiState.value =
//                        SearchCompanyUiState.Success(ss)
                }
                is CompaniesEvent.Sort -> {
                    TODO()
                }
            }
        }
    }

    companion object {
        const val NAME = "SharedViewModel"
    }
}