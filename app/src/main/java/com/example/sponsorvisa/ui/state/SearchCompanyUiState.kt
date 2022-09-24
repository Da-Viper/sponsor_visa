package com.example.sponsorvisa.ui.state

import com.example.sponsorvisa.data.Company
import kotlinx.coroutines.flow.Flow


sealed class SearchCompanyUiState {
    object Loading : SearchCompanyUiState()
    data class Success(val companies: Flow<List<Company>>) : SearchCompanyUiState()
    data class Error(val message: String) : SearchCompanyUiState()
}