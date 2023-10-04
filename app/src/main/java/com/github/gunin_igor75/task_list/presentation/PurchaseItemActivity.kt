package com.github.gunin_igor75.task_list.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.domain.pojo.Purchase.Companion.NOTING_VALUE

class PurchaseItemActivity : AppCompatActivity(), PurchaseItemFragment.OnFinishedListener {

    private var typeScreen = UNKNOWN_TYPE

    private var purchaseId = NOTING_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchMode()
        }
    }

    private fun parseIntent() {
        val intentCurrent = intent
        if (!intentCurrent.hasExtra(MODE)) {
            throw RuntimeException("Missing type")
        }
        val mode = intentCurrent.getStringExtra(MODE)
        if (mode != MODE_UPDATE && mode != MODE_ADD) {
            throw RuntimeException("Unknown type $mode")
        }
        if (mode == MODE_UPDATE && !intentCurrent.hasExtra(PURCHASE_ID)) {
            throw RuntimeException("purchase id is missing")
        }
        typeScreen = mode
        purchaseId = intentCurrent.getIntExtra(PURCHASE_ID, NOTING_VALUE)
    }
    private fun launchMode() {
        val fragment = when (typeScreen) {
            MODE_ADD -> PurchaseItemFragment.newInstanceAddItem();
            MODE_UPDATE -> PurchaseItemFragment.newInstanceUpdateItem(purchaseId)
            else -> throw RuntimeException("Unknown type $typeScreen")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_item_container,fragment)
            .commit()
    }

    companion object {
        const val MODE = "mode"
        const val MODE_ADD = "mode_add"
        const val MODE_UPDATE = "mode_update"
        const val PURCHASE_ID = "purchase_id"
        const val UNKNOWN_TYPE = ""


        fun newIntent(context: Context): Intent {
            val intent = Intent(context, PurchaseItemActivity::class.java)
            intent.putExtra(MODE, MODE_ADD)
            return intent
        }

        fun newIntent(context: Context, purchaseId: Int): Intent {
            val intent = Intent(context, PurchaseItemActivity::class.java)
            intent.putExtra(MODE, MODE_UPDATE)
            intent.putExtra(PURCHASE_ID, purchaseId)
            return intent
        }
    }

    override fun onFinished() {
        finish()
    }
}