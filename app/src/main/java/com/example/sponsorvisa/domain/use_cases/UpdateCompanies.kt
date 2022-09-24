package com.example.sponsorvisa.domain.use_cases

import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.Company

data class UpdateCompanies(
    val repository: CompanyRepository
){
    suspend operator fun invoke(companies: List<Company>){
        repository.updateCompanies(companies)
    }
}
