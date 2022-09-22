package com.example.sponsorvisa.data

import com.example.sponsorvisa.data.local.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getCompanies(): Flow<List<Company>>

    suspend fun getCompanyByName(name: String): Flow<List<Company>>

    suspend fun updateCompanies(companies: List<Company>)

    suspend fun deleteAllCompanies()
}