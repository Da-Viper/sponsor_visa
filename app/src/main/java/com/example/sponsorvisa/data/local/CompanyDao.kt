package com.example.sponsorvisa.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Query("SELECT * FROM ${Company.TABLE_NAME}")
    fun getAllCompanies(): Flow<List<Company>>

    @Query("SELECT * FROM ${Company.TABLE_NAME} where name LIKE '%' || :name || '%'")
    fun getCompanyByName(name: String): Flow<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companies: List<Company>)

    @Query("DELETE FROM ${Company.TABLE_NAME}")
    suspend fun deleteAll()
}