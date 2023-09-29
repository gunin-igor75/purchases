package com.github.gunin_igor75.task_list.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.task_list.data.impl.PurchaseRepositoryListImpl
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import com.github.gunin_igor75.task_list.domain.usecase.AddPurchaseUseCase
import com.github.gunin_igor75.task_list.domain.usecase.GetPurchaseByIdUseCase
import com.github.gunin_igor75.task_list.domain.usecase.UpdatePurchaseUseCase

const val TAG = "PurchaseItemViewModel"

class PurchaseItemViewModel : ViewModel() {

    private val purchaseRepository = PurchaseRepositoryListImpl
    private val addPurchaseUseCase = AddPurchaseUseCase(purchaseRepository)
    private val getPurchaseByIdUseCase = GetPurchaseByIdUseCase(purchaseRepository)
    private val updatePurchaseUseCase = UpdatePurchaseUseCase(purchaseRepository)

    private var _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private var _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private var _purchase = MutableLiveData<Purchase>()
    val purchase: LiveData<Purchase>
        get() = _purchase

    private var _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun addPurchase(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValidation = validateInput(name, count)
        if (isValidation) {
            val purchase = Purchase(name, count, true)
            addPurchaseUseCase.addPurchase(purchase)
            finishWork()
        }
    }

    fun updatePurchase(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValidation = validateInput(name, count)
        if (isValidation) {
            val item = _purchase.value
            item?.let {
                val purchase = it.copy(name = name, count = count)
                updatePurchaseUseCase.updatePurchase(purchase)
                finishWork()
            }
        }
    }

    fun getPurchase(purchaseId: Int) {
        val item = getPurchaseByIdUseCase.getPurchaseById(purchaseId)
        _purchase.value = item
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (ex: Exception) {
            Log.d(TAG, ex.message.toString())
            return 0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun finishWork() {
        _closeScreen.value = Unit
    }
}
