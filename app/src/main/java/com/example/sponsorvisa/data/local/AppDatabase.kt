package com.example.sponsorvisa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Company::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao

    companion object {
        const val DATABASE_NAME = "sponsor_visa"
        const val DATABASE_PATH = "database/$DATABASE_NAME.db"
    }
}