package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class DeletePurchaseUseCase(private val purchaseRepository: PurchaseRepository) {

    fun deletePurchase(purchase: Purchase) {
        purchaseRepository.deletePurchase(purchase)
    }
}