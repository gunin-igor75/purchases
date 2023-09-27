package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository

class GetPurchaseByIdUseCase(private val purchaseRepository: PurchaseRepository) {

    fun getPurchaseById(id: Int): Purchase {
        return purchaseRepository.getPurchaseById(id)
    }
}