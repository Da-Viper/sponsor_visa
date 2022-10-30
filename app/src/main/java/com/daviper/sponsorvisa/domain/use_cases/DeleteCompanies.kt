package com.daviper.sponsorvisa.domain.use_cases

import com.daviper.sponsorvisa.data.CompanyRepository

class DeleteCompanies (
    private val repository: CompanyRepository
    ) {

    suspend operator fun invoke() = repository.deleteAllCompanies()
}