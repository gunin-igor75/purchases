package com.github.gunin_igor75.task_list.data.mapper

import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import javax.inject.Inject

class PurchaseMapper @Inject constructor(){

    fun purchaseToPurchaseDbModel(purchase: Purchase) = PurchaseDbModel(
        id = purchase.id,
        name = purchase.name,
        count = purchase.count,
        enabled = purchase.enabled
    )

    fun purchaseDbModelToPurchase(purchaseDbModel: PurchaseDbModel) = Purchase(
        id = purchaseDbModel.id,
        name = purchaseDbModel.name,
        count = purchaseDbModel.count,
        enabled = purchaseDbModel.enabled
    )

    fun purchasesDBModelToPurchases(list: List<PurchaseDbModel>) = list.map {
        purchaseDbModelToPurchase(it)
    }
}