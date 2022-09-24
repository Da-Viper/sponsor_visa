package com.example.sponsorvisa.domain.utils

sealed class SortType {
    object Ascending: SortType()
    object Descending: SortType()
}
