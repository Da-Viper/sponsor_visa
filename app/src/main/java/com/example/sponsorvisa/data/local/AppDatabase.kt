package com.example.sponsorvisa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Company::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao

    companion object {
        const val DATABASE_NAME = "sponsor_visa"
    }
}