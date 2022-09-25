package com.example.sponsorvisa.data

import android.content.Context
import androidx.paging.PagingSource
import com.example.sponsorvisa.R
import com.example.sponsorvisa.data.source.local.CompanyDao
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.Flow


class CompanyRepositoryImpl(
    private val dao: CompanyDao
) :
    CompanyRepository {

    override fun getCompanies(): PagingSource<Int,Company> {
        return getLocalCompanies()
    }

    override fun getCompanyByName(name: String, isAsc: Int): PagingSource<Int,Company> {
        if(name.isBlank()){
           return getCompanies()
        }
        return dao.getCompanyByName(name, isAsc)
    }

    override suspend fun updateCompanies(companies: List<Company>) {
        dao.insertAll(companies)
    }

    override suspend fun deleteAllCompanies() {
        dao.deleteAll()
    }


    private fun getLocalCompanies(): PagingSource<Int,Company> {
        return dao.getAllCompanies()
    }


    suspend fun getAPICompaines(): PagingSource<Int,Company> {
        TODO()
    }

    private suspend fun parseCSV(context: Context): List<Company> {

        val resfile = context.resources.openRawResource(R.raw.test)
        val raa: List<Company> = csvReader().open(resfile) {
            readAllAsSequence()
                .map {
                    Company(it[0], it[1], it[2], it[3])
                }.toList()
        }
        return raa
    }

}