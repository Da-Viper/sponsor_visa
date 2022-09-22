package com.example.sponsorvisa.di

import android.app.Application
import androidx.room.Room
import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.CompanyRepositoryImpl
import com.example.sponsorvisa.data.local.AppDatabase
import com.example.sponsorvisa.data.use_cases.CompanyUseCases
import com.example.sponsorvisa.data.use_cases.DeleteCompanies
import com.example.sponsorvisa.data.use_cases.GetCompanies
import com.example.sponsorvisa.data.use_cases.UpdateCompanies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCompanyDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
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