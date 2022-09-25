package com.example.sponsorvisa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sponsorvisa.ui.state.CompaniesEvent
import com.example.sponsorvisa.ui.state.SearchCompanyUiState
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.domain.use_cases.CompanyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCompaniesUseCase: CompanyUseCases,
) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SearchCompanyUiState> =
        MutableStateFlow(SearchCompanyUiState.Loading)

    val pagingDataFlow: Flow<PagingData<Company>>

    val onUiEvent: (CompaniesEvent) -> Unit

    init {
        val actionSharedFlow = MutableSharedFlow<CompaniesEvent>()

        pagingDataFlow = createSearchFlow(actionSharedFlow)
            .flatMapLatest { getCompaniesUseCase.getCompanies(it.name) }
            .cachedIn(viewModelScope)

        onUiEvent = { event -> viewModelScope.launch { actionSharedFlow.emit(event) } }
    }

    private fun createSearchFlow(mutSharedFlow: MutableSharedFlow<CompaniesEvent>):
            Flow<CompaniesEvent.Search> {
        return mutSharedFlow
            .filterIsInstance<CompaniesEvent.Search>()
            .distinctUntilChanged()
            .onStart {
                Log.i(NAME, "search for company: Initial Query")
                emit(CompaniesEvent.Search(INITIAL_QUERY))
            }
    }

    private fun loadDatabase() {
        viewModelScope.launch {
            _uiState.emit(SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies()))
        }
    }


    val onEvent: (CompaniesEvent) -> Unit = { event ->
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
                    Log.i(NAME, "search2 : ${event.name}")
                }
                is CompaniesEvent.Sort -> {
                    TODO()
                }
            }
        }
    }

    companion object {
        const val NAME = "SharedViewModel"
        const val INITIAL_QUERY = ""
    }
}