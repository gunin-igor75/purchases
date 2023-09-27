package com.github.gunin_igor75.task_list.presentation

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.task_list.data.impl.PurchaseRepositoryListImpl
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.usecase.DeletePurchaseUseCase
import com.github.gunin_igor75.task_list.domain.usecase.GetPurchasesUseCase
import com.github.gunin_igor75.task_list.domain.usecase.UpdatePurchaseUseCase

class MainViewModel: ViewModel() {

    private val purchaseRepository = PurchaseRepositoryListImpl

    private val getPurchasesUseCase = GetPurchasesUseCase(purchaseRepository)
    private val deletePurchaseUseCase = DeletePurchaseUseCase(purchaseRepository)
    private val updatePurchaseUseCase = UpdatePurchaseUseCase(purchaseRepository)

    val purchases = getPurchasesUseCase.getPurchases()

    fun deletePurchase(purchase: Purchase) {
        deletePurchaseUseCase.deletePurchase(purchase)
    }

    fun changeEnablePurchases(purchase: Purchase) {
        val newPurchases = purchase.copy(enabled = !purchase.enabled)
        updatePurchaseUseCase.updatePurchase(newPurchases)
    }
}