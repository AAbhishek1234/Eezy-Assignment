package com.aabhishek.eezyassignment.di

import com.aabhishek.eezyassignment.framework.DoublePlanApplication
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DoublePlanViewmodelModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DoublePlanApplication): AppComponent
    }

    fun inject(activity: DoublePlanActivity)

}