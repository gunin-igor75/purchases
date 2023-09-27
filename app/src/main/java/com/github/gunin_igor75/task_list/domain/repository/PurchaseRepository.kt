package com.github.gunin_igor75.task_list.domain.repository

import androidx.lifecycle.LiveData
import com.github.gunin_igor75.task_list.domain.pojo.Purchase

interface PurchaseRepository {

    fun addPurchase(purchase: Purchase)

    fun deletePurchase(purchase: Purchase)

    fun getPurchaseById(purchaseId: Int): Purchase

    fun getPurchases(): LiveData<List<Purchase>>

    fun updatePurchase(purchase: Purchase)
}