package com.example.sponsorvisa

import com.example.sponsorvisa.data.local.Company
import com.example.sponsorvisa.utils.CompanySort
import com.example.sponsorvisa.utils.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SearchCompanyUiState(
    val notes: Flow<List<Company>> = flow { emit(emptyList()) },
    val companySort: CompanySort = CompanySort.Name(SortType.Ascending),
    val isLoading: Boolean = false,
)