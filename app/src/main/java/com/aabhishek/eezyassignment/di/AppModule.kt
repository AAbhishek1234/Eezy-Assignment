package com.aabhishek.eezyassignment.di

import androidx.room.Room
import com.aabhishek.eezyassignment.business.data.cache.DoublePlanCacheDataSource
import com.aabhishek.eezyassignment.business.data.cache.DoublePlanCacheDataSourceImpl
import com.aabhishek.eezyassignment.framework.DoublePlanApplication
import com.aabhishek.eezyassignment.framework.datasource.cache.DoublePlanDetailsService
import com.aabhishek.eezyassignment.framework.datasource.database.AppDatabase
import com.aabhishek.eezyassignment.framework.datasource.database.AppDatabase.Companion.DATABASE_NAME
import com.aabhishek.eezyassignment.framework.datasource.database.DoublePlanDetailsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDb(app: DoublePlanApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDoublePlanDetailsDao(db: AppDatabase): DoublePlanDetailsDao {
        return db.getDoublePlanDetailsDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDoublePlanDetailsService(doublePlanDetailsDao: DoublePlanDetailsDao): DoublePlanDetailsService {
        return DoublePlanDetailsService(doublePlanDetailsDao)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDoublePlanCacheDataSource(doublePlanDetailsService: DoublePlanDetailsService): DoublePlanCacheDataSource {
        return DoublePlanCacheDataSourceImpl(doublePlanDetailsService)
    }

}