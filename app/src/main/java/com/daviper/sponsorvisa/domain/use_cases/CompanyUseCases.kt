package com.daviper.sponsorvisa.domain.use_cases

data class CompanyUseCases(
    val getCompanies: GetCompanies,
    val deleteCompanies: DeleteCompanies,
    val updateCompanies: UpdateCompanies
)
