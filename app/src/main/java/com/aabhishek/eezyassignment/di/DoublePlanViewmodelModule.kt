package com.aabhishek.eezyassignment.di

import androidx.annotation.BinderThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aabhishek.eezyassignment.di.keys.DoublePlanViewModelKey
import com.aabhishek.eezyassignment.framework.presentation.viewmodel.DoublePlanViewModel
import com.aabhishek.eezyassignment.framework.presentation.viewmodel.DoublePlanViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class DoublePlanViewmodelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: DoublePlanViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @DoublePlanViewModelKey(DoublePlanViewModel::class)
    abstract fun bindDoublePlanViewModel(doublePlanViewModel: DoublePlanViewModel): ViewModel
}