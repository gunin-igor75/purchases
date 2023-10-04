package com.github.gunin_igor75.task_list.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.gunin_igor75.task_list.data.exception.PurchaseNotFoundException
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import kotlin.random.Random

object PurchaseRepositoryListImpl : PurchaseRepository {

    private val purchases = sortedSetOf<Purchase>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private val purchasesLD = MutableLiveData<List<Purchase>>()

    private var id: Int = 0

    init {
        for (i in 0 until 10) {
            val item = Purchase("name $i", i, Random.nextBoolean())
            addPurchase(item)
        }
    }

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