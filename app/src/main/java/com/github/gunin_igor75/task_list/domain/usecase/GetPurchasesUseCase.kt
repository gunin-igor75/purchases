package com.github.gunin_igor75.task_list.domain.usecase

import androidx.lifecycle.LiveData
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.repository.PurchaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class GetPurchasesUseCase @Inject constructor(
    @Named("sqlite") private val purchaseRepository: PurchaseRepository
) {

    suspend fun getPurchases(): Flow<List<Purchase>> {
        return purchaseRepository.getPurchases()
    }
}