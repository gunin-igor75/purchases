package com.github.gunin_igor75.task_list.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.gunin_igor75.task_list.data.db.PurchaseDao
import com.github.gunin_igor75.task_list.data.db.PurchaseDataBase
import com.github.gunin_igor75.task_list.data.impl.PurchaseRepositoryDBImp
import com.github.gunin_igor75.task_list.data.impl.PurchaseRepositorySqliteImp
import com.github.gunin_igor75.task_list.data.sqlite_db.AppSqliteHelper
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
interface DataModule {

    @AppScope
    @Binds
    @Named("room")
    fun bindsPurchaseRepositoryRoom(impl: PurchaseRepositoryDBImp): PurchaseRepository

    @AppScope
    @Binds
    @Named("sqlite")
    fun bindsPurchaseRepositorySqlite(impl: PurchaseRepositorySqliteImp): PurchaseRepository

    companion object{

        @AppScope
        @Provides
        fun providesDatabase(applicationContext: Context): PurchaseDao {
            return PurchaseDataBase.getInstance(applicationContext).purchaseDao()
        }

        @AppScope
        @Provides
        fun providesSqliteDB(applicationContext: Context): SQLiteDatabase {
            return AppSqliteHelper(applicationContext).writableDatabase
        }

    }
}