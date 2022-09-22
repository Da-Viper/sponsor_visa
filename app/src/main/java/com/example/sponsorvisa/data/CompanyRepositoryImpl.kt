package com.example.sponsorvisa.data

import android.content.Context
import com.example.sponsorvisa.R
import com.example.sponsorvisa.data.local.Company
import com.example.sponsorvisa.data.local.CompanyDao
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.Flow


class CompanyRepositoryImpl(
    private val dao: CompanyDao
) :
    CompanyRepository {

    override fun getCompanies(): Flow<List<Company>> {
        return getLocalCompanies()
    }

    override suspend fun getCompanyByName(name: String): Flow<List<Company>> {
        return dao.getCompanyByName(name)
    }

    override suspend fun updateCompanies(companies: List<Company>) {
        dao.insertAll(companies)
    }

    override suspend fun deleteAllCompanies() {
        dao.deleteAll()
    }


    private fun getLocalCompanies(): Flow<List<Company>> {
        return dao.getAllCompanies()
    }


    suspend fun getAPICompaines(): List<Company> {
        TODO()
    }

    private suspend fun parseCSV(context: Context): List<Company> {

        val resfile = context.resources.openRawResource(R.raw.test)
        val raa: List<Company> = csvReader().open(resfile) {
            readAllAsSequence()
                .map {
                    Company(it[0], it[1], it[2], it[3], it[4].toInt())
                }.toList()
        }
        return raa
    }

}