package com.example.sponsorvisa.data.local

import androidx.room.Entity

@Entity(tableName = Company.TABLE_NAME)
data class Company(
    val name: String,
    val city: String?,
    val rating: String?,
    val route: String,
){
    companion object {
        const val TABLE_NAME = "Company"
    }
}