package com.github.gunin_igor75.task_list.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel

@Database(entities = [PurchaseDbModel::class], version = 1, exportSchema = false)
abstract class PurchaseDataBase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        private const val DB_NAME = "purchase.db"

        private val LOCK = Any()

        private var instance: PurchaseDataBase? = null

        fun getInstance(application: Application): PurchaseDataBase {
            instance?.let {
                return it
            }
            synchronized(LOCK) {
                val db = Room.databaseBuilder(
                    application,
                    PurchaseDataBase::class.java,
                    DB_NAME
                ).build()
                instance = db
                return db
            }
        }
    }
}