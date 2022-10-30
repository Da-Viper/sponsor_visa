package com.daviper.sponsorvisa.di

import android.app.Application
import androidx.room.Room
import com.daviper.sponsorvisa.data.CompanyRepository
import com.daviper.sponsorvisa.data.CompanyRepositoryImpl
import com.daviper.sponsorvisa.data.local.AppDatabase
import com.daviper.sponsorvisa.domain.use_cases.CompanyUseCases
import com.daviper.sponsorvisa.domain.use_cases.DeleteCompanies
import com.daviper.sponsorvisa.domain.use_cases.GetCompanies
import com.daviper.sponsorvisa.domain.use_cases.UpdateCompanies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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