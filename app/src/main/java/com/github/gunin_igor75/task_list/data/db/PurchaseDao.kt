package com.github.gunin_igor75.task_list.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchases ORDER BY id")
    fun getPurchases(): Flow<List<PurchaseDbModel>>

    @Query("SELECT * FROM purchases ORDER BY id")
    fun getPurchasesCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPurchase(purchaseDbModel: PurchaseDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPurchaseProvider(purchaseDbModel: PurchaseDbModel)

    @Query("DELETE FROM purchases WHERE id =:purchaseId")
    suspend fun deletePurchase(purchaseId: Int)

    @Query("DELETE FROM purchases WHERE id =:purchaseId")
    fun deletePurchaseProvider(purchaseId: Int): Int

    @Query("SELECT * FROM purchases WHERE id =:purchaseId LIMIT 1")
    suspend fun getPurchaseById(purchaseId: Int): PurchaseDbModel

    @Query("SELECT * FROM purchases WHERE id =:purchaseId LIMIT 1")
    fun getPurchaseByIdCursor(purchaseId: Int): Cursor

}