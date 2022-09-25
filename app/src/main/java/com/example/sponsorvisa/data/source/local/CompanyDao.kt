package com.example.sponsorvisa.data.source.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.sponsorvisa.data.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Query("SELECT * FROM ${Company.TABLE_NAME}")
    fun getAllCompanies(): PagingSource<Int, Company>

    @Query(
        "SELECT * FROM ${Company.TABLE_NAME} " +
                "WHERE name LIKE '%' || :name || '%' " +
                "ORDER BY " +
                "CASE WHEN :isAsc = 0 THEN LOWER(name) END DESC," +
                "CASE WHEN :isAsc = 1 THEN LOWER(name) END ASC "
    )
    fun getCompanyByName(name: String, isAsc: Int): PagingSource<Int, Company>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companies: List<Company>)

    @Query("DELETE FROM ${Company.TABLE_NAME}")
    suspend fun deleteAll()
}