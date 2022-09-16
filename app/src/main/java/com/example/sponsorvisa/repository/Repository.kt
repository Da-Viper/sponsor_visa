package com.example.sponsorvisa.repository

import android.app.Application
import com.example.sponsorvisa.R
import com.example.sponsorvisa.model.Company
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader


class Repository(private val application: Application) {

    fun getLocalCompanies(): List<Company> {

        return parseCSV()
    }

    private fun parseCSV(): List<Company> {

        val resfile = application.applicationContext.resources.openRawResource(R.raw.test)
        val raa: List<Company> = csvReader().open(resfile) {
            readAllAsSequence()
                .map {
                    Company(it[0], it[1], it[2], it[3])
                }.toList()
        }
        return raa
    }
}