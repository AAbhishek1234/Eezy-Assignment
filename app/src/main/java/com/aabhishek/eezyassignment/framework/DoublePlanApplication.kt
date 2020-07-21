package com.aabhishek.eezyassignment.framework

import android.app.Application
import com.aabhishek.eezyassignment.di.AppComponent
import com.aabhishek.eezyassignment.di.DaggerAppComponent

class DoublePlanApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.factory().create(this)
    }
}