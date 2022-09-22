package com.example.sponsorvisa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Company.TABLE_NAME)
data class Company(
    val name: String,
    val city: String?,
    val rating: String?,
    val route: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
) {
    companion object {
        const val TABLE_NAME = "Company"
    }
}