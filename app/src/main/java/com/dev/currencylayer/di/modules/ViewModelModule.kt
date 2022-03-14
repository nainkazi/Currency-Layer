package com.dev.currencylayer.di.modules

import androidx.lifecycle.ViewModel
import com.dev.currencylayer.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModel(homeViewModel: HomeViewModel): ViewModel

}