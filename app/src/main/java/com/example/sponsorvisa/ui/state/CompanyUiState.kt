package com.example.sponsorvisa.ui.state

import androidx.paging.PagingData
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.domain.utils.CompanySort
import com.example.sponsorvisa.domain.utils.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


sealed class CompanyUiState {
    object Loading : CompanyUiState()
    data class Success(val companies: Flow<PagingData<Company>>) :
        CompanyUiState()

    data class Error(val message: String) : CompanyUiState()
}

data class SearchUiState(
    val filter: CompanySort = CompanySort.Name(SortType.Ascending)
)