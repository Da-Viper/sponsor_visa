package com.example.sponsorvisa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sponsorvisa.ui.state.CompaniesEvent
import com.example.sponsorvisa.ui.state.SearchCompanyUiState
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.domain.use_cases.CompanyUseCases
import com.example.sponsorvisa.domain.utils.CompanySort
import com.example.sponsorvisa.domain.utils.SortType
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
    val uiState = _uiState.asStateFlow()

    private val _actionSharedFlow = MutableSharedFlow<CompaniesEvent>()

    private var _sortType: CompanySort = CompanySort.Name(SortType.Ascending)

    private val _orderState = MutableStateFlow(Boolean)
    val orderState = _orderState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = SearchCompanyUiState.Success(createSearchFlow(_actionSharedFlow))
        }
        loadDatabase(_actionSharedFlow)

    }

    private fun createSearchFlow(mutSharedFlow: MutableSharedFlow<CompaniesEvent>):
            Flow<PagingData<Company>> {
        return mutSharedFlow
            .filterIsInstance<CompaniesEvent.Search>()
            .distinctUntilChanged()
            .onStart {
                Log.i(NAME, "search for company: Initial Query")
                emit(CompaniesEvent.Search(INITIAL_QUERY))
            }
            .flatMapLatest { getCompaniesUseCase.getCompanies(it.name, _sortType) }
            .cachedIn(viewModelScope)
    }

    private fun loadDatabase(mutSharedFlow: MutableSharedFlow<CompaniesEvent>) {
        viewModelScope.launch {
            mutSharedFlow
                .filterIsInstance<CompaniesEvent.Load>()
                .distinctUntilChanged()
                .collectLatest {
                    _uiState.value =
                        SearchCompanyUiState.Success(getCompaniesUseCase.getCompanies())
                }
        }
    }

    fun onUiEvent(event: CompaniesEvent) {
        when (event) {
            is CompaniesEvent.Search -> {
                viewModelScope.launch {
                    Log.i(NAME, "sortType: ${_sortType.sortType}")
                    _uiState.value = SearchCompanyUiState.Success(
                        getCompaniesUseCase.getCompanies(
                            event.name, _sortType
                        )
                    )
                }
            }

            is CompaniesEvent.Load -> {
                // TODO Load database
            }
            is CompaniesEvent.Sort -> {
                Log.i(NAME, "got to changing ${_sortType.sortType}")
                _sortType = event.companySort
                Log.i(NAME, "got to changing ${_sortType.sortType}")
            }
        }
    }

    companion object {
        const val NAME = "SharedViewModel"
        const val INITIAL_QUERY = ""
    }
}