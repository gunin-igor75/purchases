package com.github.gunin_igor75.task_list.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchases ORDER BY id")
    fun getPurchases(): LiveData<List<PurchaseDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPurchase(purchaseDbModel: PurchaseDbModel)

    @Query("DELETE FROM purchases WHERE id =:purchaseId")
    suspend fun deletePurchase(purchaseId: Int)

    @Query("SELECT * FROM purchases WHERE id =:purchaseId LIMIT 1")
    suspend fun getPurchaseById(purchaseId: Int): PurchaseDbModel

}