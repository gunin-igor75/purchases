package com.github.gunin_igor75.task_list.domain.usecase

import androidx.lifecycle.LiveData
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class GetPurchasesUseCase(private val purchaseRepository: PurchaseRepository) {

    fun getPurchases(): LiveData<List<Purchase>> {
        return purchaseRepository.getPurchases()
    }
}