package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import javax.inject.Inject

class AddPurchaseUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {

    suspend fun addPurchase(purchase: Purchase) {
        purchaseRepository.addPurchase(purchase)
    }
}