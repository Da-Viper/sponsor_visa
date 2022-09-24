package com.example.sponsorvisa.data

import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getCompanies(): Flow<List<Company>>

    fun getCompanyByName(name: String): Flow<List<Company>>

    suspend fun updateCompanies(companies: List<Company>)

    suspend fun deleteAllCompanies()
}