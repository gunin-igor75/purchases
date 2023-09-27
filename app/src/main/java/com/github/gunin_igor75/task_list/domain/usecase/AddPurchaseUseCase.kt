package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class AddPurchaseUseCase(private val purchaseRepository: PurchaseRepository) {

    fun addPurchase(purchase: Purchase) {
        purchaseRepository.addPurchase(purchase)
    }
}