package com.example.sponsorvisa.data.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
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