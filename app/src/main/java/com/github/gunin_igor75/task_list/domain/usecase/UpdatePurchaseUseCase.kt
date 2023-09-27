package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class UpdatePurchaseUseCase(private val purchaseRepository: PurchaseRepository) {

    fun updatePurchase(purchase: Purchase) {
        purchaseRepository.updatePurchase(purchase)
    }
}