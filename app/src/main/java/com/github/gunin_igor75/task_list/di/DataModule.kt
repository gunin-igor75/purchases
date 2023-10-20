package com.github.gunin_igor75.task_list.di

import android.app.Application
import com.github.gunin_igor75.task_list.data.db.PurchaseDao
import com.github.gunin_igor75.task_list.data.db.PurchaseDataBase
import com.github.gunin_igor75.task_list.data.impl.PurchaseRepositoryDBImp
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindsPurchaseRepository(impl: PurchaseRepositoryDBImp): PurchaseRepository

    companion object{

        @Provides
        fun providesDatabase(application: Application): PurchaseDao {
            return PurchaseDataBase.getInstance(application).purchaseDao()
        }
    }
}