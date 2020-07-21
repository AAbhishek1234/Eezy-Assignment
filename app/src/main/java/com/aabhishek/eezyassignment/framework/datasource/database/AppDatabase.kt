package com.aabhishek.eezyassignment.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails

@Database(entities = [DoublePlanDetails::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getDoublePlanDetailsDao(): DoublePlanDetailsDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}