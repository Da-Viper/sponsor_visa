package com.example.sponsorvisa.data

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getCompanies(): PagingSource<Int,Company>

    fun getCompanyByName(name: String, isAsc: Int): PagingSource<Int,Company>

    suspend fun updateCompanies(companies: List<Company>)

    suspend fun deleteAllCompanies()
}