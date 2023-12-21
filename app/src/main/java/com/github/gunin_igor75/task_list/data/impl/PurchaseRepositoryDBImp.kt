package com.github.gunin_igor75.task_list.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.gunin_igor75.task_list.data.db.PurchaseDao
import com.github.gunin_igor75.task_list.data.mapper.PurchaseMapper
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryDBImp @Inject constructor(
    private val purchaseDao: PurchaseDao,
    private val mapper: PurchaseMapper
) : PurchaseRepository {


    override suspend fun addPurchase(purchase: Purchase) {
        val purchaseDbModel = mapper.purchaseToPurchaseDbModel(purchase)
        purchaseDao.addPurchase(purchaseDbModel)
    }

    override suspend fun deletePurchase(purchase: Purchase) {
        purchaseDao.deletePurchase(purchase.id)
    }

    override suspend fun getPurchaseById(purchaseId: Int): Purchase {
        val purchaseDbModel = purchaseDao.getPurchaseById(purchaseId)
        return mapper.purchaseDbModelToPurchase(purchaseDbModel)
    }

    override fun getPurchases(): LiveData<List<Purchase>> {
        return purchaseDao.getPurchases().map { mapper.purchasesDBModelToPurchases(it) }
    }

    override suspend fun updatePurchase(purchase: Purchase) {
        addPurchase(purchase)
    }
}