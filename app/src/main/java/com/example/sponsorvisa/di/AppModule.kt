package com.example.sponsorvisa.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.CompanyRepositoryImpl
import com.example.sponsorvisa.data.local.AppDatabase
import com.example.sponsorvisa.data.use_cases.CompanyUseCases
import com.example.sponsorvisa.data.use_cases.DeleteCompanies
import com.example.sponsorvisa.data.use_cases.GetCompanies
import com.example.sponsorvisa.data.use_cases.UpdateCompanies
import com.example.sponsorvisa.utils.parseCSV
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private lateinit var pal: AppDatabase

    @Singleton
    @Provides
    fun provideCompanyDatabase(app: Application): AppDatabase {
        pal = Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .createFromAsset(AppDatabase.DATABASE_PATH)
            .build()
        return pal
    }

    @Singleton
    @Provides
    fun provideCompanyRepository(appDB: AppDatabase): CompanyRepository {
        return CompanyRepositoryImpl(appDB.companyDao())
    }

    @Provides
    fun provideCompanyUseCases(repository: CompanyRepository): CompanyUseCases {
        return CompanyUseCases(
            getCompanies = GetCompanies(repository),
            deleteCompanies = DeleteCompanies(repository),
            updateCompanies = UpdateCompanies(repository)
        )
    }
}