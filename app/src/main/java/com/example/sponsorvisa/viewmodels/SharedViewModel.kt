package com.example.sponsorvisa.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.sponsorvisa.CompaniesEvent
import com.example.sponsorvisa.SearchCompanyUiState
import com.example.sponsorvisa.asList
import com.example.sponsorvisa.data.local.Company
import com.example.sponsorvisa.data.use_cases.CompanyUseCases
import com.example.sponsorvisa.utils.parseCSV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCompaniesUseCase: CompanyUseCases,
//    private val savedStateHandle: SavedStateHandle,
    application: Application,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SearchCompanyUiState(isLoading = true))
    val uiState: StateFlow<SearchCompanyUiState> = _uiState

    init {
//        parseCSV(application.baseContext).run {
//            populateDatabase(this)
//        }

    }

    private fun populateDatabase(companies: List<Company>) {
        viewModelScope.launch {
            getCompaniesUseCase.updateCompanies(companies)
        }
    }

    suspend fun getCompanies(): Flow<List<Company>> {
        return getCompaniesUseCase.getCompanies()
    }
    fun onEvent(event: CompaniesEvent) {
        viewModelScope.launch {
            when (event) {
                is CompaniesEvent.Load -> {
//                    _uiState.update { cState ->
//                        cState.copy(
////                            companies = getCompaniesUseCase.getCompanies(),
//                            companies = flow { emit(parseCSV(app.baseContext)) },
//                            isLoading = false
//                        )
//                    }
                    _uiState.value = SearchCompanyUiState(getCompaniesUseCase.getCompanies())
                }
                is CompaniesEvent.Search -> {
                    TODO()
                }
                is CompaniesEvent.Sort -> {
                    TODO()
                }
            }
        }
    }
}