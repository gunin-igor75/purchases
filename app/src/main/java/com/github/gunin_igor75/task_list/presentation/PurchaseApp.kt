package com.github.gunin_igor75.task_list.presentation

import android.app.Application
import com.github.gunin_igor75.task_list.di.DaggerApplicationComponent

class PurchaseApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}