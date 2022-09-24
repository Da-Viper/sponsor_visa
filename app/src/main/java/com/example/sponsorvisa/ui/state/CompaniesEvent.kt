package com.example.sponsorvisa.ui.state

import com.example.sponsorvisa.domain.utils.CompanySort

sealed class CompaniesEvent {
    object Load : CompaniesEvent()
    data class Search(val name: String): CompaniesEvent()
    data class Sort(val companySort: CompanySort): CompaniesEvent()
//    data class Filter(): CompaniesEvent()
}
