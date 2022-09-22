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
    private lateinit var pal: AppDatabase

    @Singleton
    @Provides
    fun provideCompanyDatabase(app: Application): AppDatabase {
        pal = Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                     moving to a new thread
//                    MainScope().launch {
//                        parseCSV(app.baseContext).run {
//                            pal.companyDao().insertAll(this)
//                        }
//                    }
//                        getInstance(context).dataDao()
//                            .insert(PREPOPULATE_DATA)
//                    }
//                }
//            })
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