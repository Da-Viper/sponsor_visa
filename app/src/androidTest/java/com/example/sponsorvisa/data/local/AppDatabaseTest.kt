package com.example.sponsorvisa.data.local

import android.util.Log
import androidx.test.filters.SmallTest
import com.example.sponsorvisa.data.source.local.AppDatabase
import com.example.sponsorvisa.data.Company
import com.example.sponsorvisa.data.source.local.CompanyDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class AppDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var companyDao: CompanyDao

    @Inject
    @Named("test_db")
    lateinit var db: AppDatabase

    @Before
    fun createDb() {
        hiltRule.inject()
        companyDao = db.companyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testReadDatabase() = runTest {
        val companies: List<Company> = companyDao.getCompanyByName("london").first()
        Log.d(NAME, "companies: $companies")
        assert(companies.isNotEmpty())
    }

    fun testUpdateDatabase(): Unit = TODO()



    companion object {
        val NAME = this.toString()
    }
}