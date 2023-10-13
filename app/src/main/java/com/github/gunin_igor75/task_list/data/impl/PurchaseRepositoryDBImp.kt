package com.github.gunin_igor75.task_list.data.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.github.gunin_igor75.task_list.data.db.PurchaseDataBase
import com.github.gunin_igor75.task_list.data.mapper.PurchaseMapper
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class PurchaseRepositoryDBImp(application: Application): PurchaseRepository {

    private val purchaseDao = PurchaseDataBase.getInstance(application).purchaseDao()

    private val mapper = PurchaseMapper()

    override fun addPurchase(purchase: Purchase) {
        val purchaseDbModel = mapper.purchaseToPurchaseDbModel(purchase)
        purchaseDao.addPurchase(purchaseDbModel)
    }

    override fun deletePurchase(purchase: Purchase) {
        purchaseDao.deletePurchase(purchase.id)
    }

    override fun getPurchaseById(purchaseId: Int): Purchase {
        val purchaseDbModel = purchaseDao.getPurchaseById(purchaseId)
        return mapper.purchaseDbModelToPurchase(purchaseDbModel)
    }

    override fun getPurchases(): LiveData<List<Purchase>> {
        return purchaseDao.getPurchases().map { mapper.purchasesDBModelToPurchases(it) }
    }

    override fun updatePurchase(purchase: Purchase) {
        addPurchase(purchase)
    }
}