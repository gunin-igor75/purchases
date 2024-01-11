package com.github.gunin_igor75.task_list.domain.usecase

import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import javax.inject.Inject
import javax.inject.Named

class GetPurchaseByIdUseCase @Inject constructor(
    @Named("sqlite") private val purchaseRepository: PurchaseRepository
) {

    suspend fun getPurchaseById(id: Int): Purchase {
        return purchaseRepository.getPurchaseById(id)
    }
}