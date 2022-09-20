package com.example.sponsorvisa.di

import android.app.Application
import androidx.room.Room
import com.example.sponsorvisa.data.CompanyRepository
import com.example.sponsorvisa.data.CompanyRepositoryImpl
import com.example.sponsorvisa.data.local.AppDatabase
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

}