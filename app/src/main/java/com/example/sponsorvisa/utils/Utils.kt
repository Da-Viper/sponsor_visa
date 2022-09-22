package com.example.sponsorvisa.utils

import android.content.Context
import com.example.sponsorvisa.R
import com.example.sponsorvisa.data.local.Company
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun parseCSV(context: Context): List<Company> {

    val resfile = context.resources.openRawResource(R.raw.test)
    val raa: List<Company> = csvReader().open(resfile) {
        readAllAsSequence()
            .map {
                Company(it[0], it[1], it[2], it[3])
            }.toList()
    }
    return raa
}