package com.github.gunin_igor75.task_list.domain.repository

import androidx.lifecycle.LiveData
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    suspend fun addPurchase(purchase: Purchase)

    suspend fun deletePurchase(purchase: Purchase)

    suspend fun getPurchaseById(purchaseId: Int): Purchase

    suspend fun getPurchases(): Flow<List<Purchase>>

    suspend fun updatePurchase(purchase: Purchase)
}