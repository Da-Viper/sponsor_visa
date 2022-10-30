package com.daviper.sponsorvisa.domain.use_cases

import com.daviper.sponsorvisa.data.CompanyRepository
import com.daviper.sponsorvisa.data.Company

data class UpdateCompanies(
    val repository: CompanyRepository
){
    suspend operator fun invoke(companies: List<Company>){
        repository.updateCompanies(companies)
    }
}
