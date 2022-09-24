package com.example.sponsorvisa.domain.utils

sealed class CompanySort(val sortType: SortType) {
    class Name(sortType: SortType): CompanySort(sortType)
    class City(sortType: SortType): CompanySort(sortType)

}