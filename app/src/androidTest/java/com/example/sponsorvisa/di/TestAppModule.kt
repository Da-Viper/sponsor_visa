package com.example.sponsorvisa.di

import android.content.Context
import androidx.room.Room
import com.example.sponsorvisa.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    private lateinit var pal: AppDatabase

    @Provides
    @Named("test_db")
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        pal =  Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .createFromAsset(AppDatabase.DATABASE_PATH)
            .build()
        return pal
    }
}