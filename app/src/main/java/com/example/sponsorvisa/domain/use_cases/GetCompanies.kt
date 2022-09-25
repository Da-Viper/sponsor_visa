package com.example.sponsorvisa.domain.use_cases

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.domain.utils.CompanySort
import com.example.sponsorvisa.domain.utils.SortType
import kotlinx.coroutines.flow.Flow

class GetCompanies(
    private val repository: CompanyRepository
) {
    operator fun invoke(
        query: String = "",
        companySort: CompanySort = CompanySort.Name(SortType.Ascending)
    ): Flow<PagingData<Company>> {

        val isAsc: Int = when (companySort.sortType) {
            is SortType.Ascending -> 1
            is SortType.Descending -> 0
        }

        Log.i("GetCompanies", "query: $query, sort: ${companySort.sortType}, asc: $isAsc")
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { repository.getCompanyByName(query, isAsc) }
        ).flow
    }
//    operator fun invoke(
//        name: String = "",
//        companySort: CompanySort = CompanySort.Name(SortType.Ascending)
//    ): Flow<List<Company>> {
//        return repository.getCompanyByName(name).map { companies ->
//            when (companySort.sortType) {
//                is SortType.Ascending -> {
//                    when (companySort) {
//                        is CompanySort.Name -> companies.sortedBy { it.name.lowercase() }
//                        is CompanySort.City -> companies.sortedBy { it.city?.lowercase() }
//                    }
//                }
//                is SortType.Descending -> {
//                    when (companySort) {
//                        is CompanySort.Name -> companies.sortedByDescending { it.name.lowercase() }
//                        is CompanySort.City -> companies.sortedByDescending { it.city?.lowercase() }
//                    }
//                }
//            }
//        }
//    }
}
