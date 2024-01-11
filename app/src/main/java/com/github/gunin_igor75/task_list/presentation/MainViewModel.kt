package com.github.gunin_igor75.task_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.usecase.DeletePurchaseUseCase
import com.github.gunin_igor75.task_list.domain.usecase.GetPurchasesUseCase
import com.github.gunin_igor75.task_list.domain.usecase.UpdatePurchaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPurchasesUseCase: GetPurchasesUseCase,
    private val deletePurchaseUseCase: DeletePurchaseUseCase,
    private val updatePurchaseUseCase: UpdatePurchaseUseCase
) : ViewModel() {

    suspend fun purchases(): Flow<List<Purchase>> {
        return getPurchasesUseCase.getPurchases()
            .filter { it.isNotEmpty() }
    }


    fun deletePurchase(purchase: Purchase) {
        viewModelScope.launch {
            deletePurchaseUseCase.deletePurchase(purchase)
        }
    }

    fun changeEnablePurchases(purchase: Purchase) {
        val newPurchases = purchase.copy(enabled = !purchase.enabled)
        viewModelScope.launch {
            updatePurchaseUseCase.updatePurchase(newPurchases)
        }
    }
}