package com.daviper.sponsorvisa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Company.TABLE_NAME)
data class Company(
    val name: String,
    val city: String?,
    val rating: String?,
    val route: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val TABLE_NAME = "Company"
    }
}