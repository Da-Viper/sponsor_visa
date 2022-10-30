package com.daviper.sponsorvisa.domain.utils

import android.content.Context
import com.daviper.sponsorvisa.R
import com.daviper.sponsorvisa.data.Company
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.util.Optional


suspend fun getRemoteCSVLink(): Optional<String> {
   TODO()
}

suspend fun getRemoteCSV(): File? {
   TODO()
}

suspend fun parseCSV(context: Context, src: File): List<Company> {

    // TODO make this function async
    println("parsing csv")
    val resfile = context.resources.openRawResource(R.raw.test)
    val raa: List<Company> = csvReader().openAsync(resfile) {
        readAllAsSequence()
            .map {
                Company(it[0], it[1], it[2], it[3])
            }.toList()
    }
    return raa
}
