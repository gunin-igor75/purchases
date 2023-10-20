package com.github.gunin_igor75.task_list.di

import android.app.Application
import com.github.gunin_igor75.task_list.presentation.MainActivity
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity
import com.github.gunin_igor75.task_list.presentation.PurchaseItemFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules =
    [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: PurchaseItemActivity)

    fun inject(fragment: PurchaseItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}