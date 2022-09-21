package com.example.sponsorvisa

import android.app.Application
import androidx.lifecycle.*
import com.example.sponsorvisa.data.local.Company
import com.example.sponsorvisa.data.use_cases.CompanyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCompaniesUseCase: CompanyUseCases,
    application: Application
) :
    AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SearchCompanyUiState(isLoading = true))
    val uiState: StateFlow<SearchCompanyUiState> = _uiState.asStateFlow()


    fun onEvent(event: CompaniesEvent) {
        viewModelScope.launch {
            when (event) {
                is CompaniesEvent.Load -> {
                    _uiState.update { cState ->
                        cState.copy(notes = getCompaniesUseCase.getCompanies(), isLoading = false)
                    }
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