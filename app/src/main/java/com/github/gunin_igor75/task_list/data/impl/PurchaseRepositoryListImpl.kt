package com.github.gunin_igor75.task_list.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.gunin_igor75.task_list.data.exception.PurchaseNotFoundException
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

object PurchaseRepositoryListImpl : PurchaseRepository {

    private val purchases = mutableListOf<Purchase>()

    private val purchasesLD = MutableLiveData<List<Purchase>>()

    private var id: Int = 0

    override fun addPurchase(purchase: Purchase) {
        val purchaseId = purchase.id
        if (purchaseId == Purchase.NOTING_VALUE) {
            purchase.id = id++
        }
        purchases.add(purchase)
        updatePurchaseLD()
    }

    override fun deletePurchase(purchase: Purchase) {
        purchases.remove(purchase)
        updatePurchaseLD()
    }

    override fun getPurchaseById(purchaseId: Int): Purchase {
        return purchases.find { it.id == purchaseId }
            ?: throw PurchaseNotFoundException("Purchase with id $purchaseId not found")
    }

    override fun getPurchases(): LiveData<List<Purchase>> {
        return purchasesLD
    }

    override fun updatePurchase(purchase: Purchase) {
        val oldPurchase = getPurchaseById(purchase.id)
        purchases.remove(oldPurchase)
        addPurchase(purchase)
    }

    private fun updatePurchaseLD() {
        purchasesLD.value = purchases.toList()
    }
}