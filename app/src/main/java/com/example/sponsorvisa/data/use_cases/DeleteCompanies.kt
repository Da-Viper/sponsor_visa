package com.example.sponsorvisa.data.use_cases

import com.example.sponsorvisa.data.CompanyRepository

class DeleteCompanies (
    private val repository: CompanyRepository
    ) {

    suspend operator fun invoke() = repository.deleteAllCompanies()
}