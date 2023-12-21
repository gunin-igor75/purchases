package com.github.gunin_igor75.task_list.di

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.task_list.presentation.MainViewModel
import com.github.gunin_igor75.task_list.presentation.PurchaseItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PurchaseItemViewModel::class)
    @Binds
    fun bindsPurchaseItemViewModel(viewModel: PurchaseItemViewModel): ViewModel


}