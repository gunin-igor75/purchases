package com.github.gunin_igor75.task_list.data.impl

import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.github.gunin_igor75.task_list.data.exception.AppException
import com.github.gunin_igor75.task_list.data.mapper.PurchaseMapper
import com.github.gunin_igor75.task_list.data.sqlite_db.AppSqliteContract.PurchaseTable
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PurchaseRepositorySqliteImp @Inject constructor(
    private val db: SQLiteDatabase,
    private val mapper: PurchaseMapper,
) : PurchaseRepository {

    private val ioDispatcher = Dispatchers.IO
    private val _purchases = MutableStateFlow<List<Purchase>>(listOf())
    private val purchases = _purchases.asStateFlow()

    override suspend fun addPurchase(purchase: Purchase) {
        withContext(ioDispatcher) {
            val purchaseDbModel = mapper.purchaseToPurchaseDbModel(purchase)
            try {
                db.insertOrThrow(
                    PurchaseTable.TABLE_NAME,
                    null,
                    contentValuesOf(
                        PurchaseTable.COLUMN_NAME to purchaseDbModel.name,
                        PurchaseTable.COLUMN_COUNT to purchaseDbModel.count,
                        PurchaseTable.COLUMN_ENABLED to purchaseDbModel.enabled
                    )
                )
            } catch (e: SQLiteConstraintException) {
                val appException = AppException()
                appException.initCause(e)
                throw appException
            }
        }
    }

    override suspend fun deletePurchase(purchase: Purchase) {
        withContext(ioDispatcher) {
            db.delete(
                PurchaseTable.TABLE_NAME,
                "${PurchaseTable.COLUMN_ID} = ?",
                arrayOf(purchase.id.toString())
            )
        }
    }

    override suspend fun getPurchaseById(purchaseId: Int): Purchase {
        var purchase: Purchase?
        withContext(ioDispatcher){
            val cursor = db.query(
                PurchaseTable.TABLE_NAME,
                arrayOf(
                    PurchaseTable.COLUMN_ID,
                    PurchaseTable.COLUMN_NAME,
                    PurchaseTable.COLUMN_COUNT,
                    PurchaseTable.COLUMN_ENABLED
                ),
                "${PurchaseTable.COLUMN_ID} = ?",
                arrayOf(purchaseId.toString()),
                null, null, null
            )
            purchase = cursor.use {
                if (cursor.count == 0) return@use null
                cursor.moveToFirst()
                parsePurchase(cursor)
            }
        }
        return purchase ?: throw AppException()
    }

    override suspend fun getPurchases(): Flow<List<Purchase>> {
        val list = mutableListOf<Purchase>()
        withContext(ioDispatcher) {
            val cursor = db.query(
                PurchaseTable.TABLE_NAME,
                arrayOf(
                    PurchaseTable.COLUMN_ID,
                    PurchaseTable.COLUMN_NAME,
                    PurchaseTable.COLUMN_COUNT,
                    PurchaseTable.COLUMN_ENABLED
                ),
                null, null, null, null, PurchaseTable.COLUMN_ID
            )
            cursor.use {
                while (cursor.moveToNext()) {
                    list.add(parsePurchase(cursor))
                }
            }
        }
        _purchases.value = list
        return purchases
    }

    private fun parsePurchase(cursor: Cursor): Purchase {
        return Purchase(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(PurchaseTable.COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(PurchaseTable.COLUMN_NAME)),
            count = cursor.getInt(cursor.getColumnIndexOrThrow(PurchaseTable.COLUMN_COUNT)),
            enabled = cursor.getInt(cursor.getColumnIndexOrThrow(PurchaseTable.COLUMN_ENABLED)) == 1,
        )
    }

    override suspend fun updatePurchase(purchase: Purchase) {
        withContext(ioDispatcher) {
            db.updateWithOnConflict(
                PurchaseTable.TABLE_NAME,
                contentValuesOf(
                    PurchaseTable.COLUMN_NAME to purchase.name,
                    PurchaseTable.COLUMN_COUNT to purchase.count,
                    PurchaseTable.COLUMN_ENABLED to purchase.enabled
                ),
                "${PurchaseTable.COLUMN_ID} = ?",
                arrayOf(purchase.id.toString()),
                SQLiteDatabase.CONFLICT_REPLACE
            )
        }
    }
}