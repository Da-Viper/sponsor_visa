package com.example.sponsorvisa.data.use_cases

import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.local.Company

data class UpdateCompanies(
    val repository: CompanyRepository
){
    suspend operator fun invoke(companies: List<Company>){
        repository.updateCompanies(companies)
    }
}
