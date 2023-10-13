package com.github.gunin_igor75.task_list.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel
import com.github.gunin_igor75.task_list.domain.pojo.Purchase

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchases ORDER BY id")
    fun getPurchases(): LiveData<List<PurchaseDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPurchase(purchaseDbModel: PurchaseDbModel)

    @Query("DELETE FROM purchases WHERE id =:purchaseId")
    fun deletePurchase(purchaseId: Int)

    @Query("SELECT * FROM purchases WHERE id =:purchaseId LIMIT 1")
    fun getPurchaseById(purchaseId: Int): PurchaseDbModel

}