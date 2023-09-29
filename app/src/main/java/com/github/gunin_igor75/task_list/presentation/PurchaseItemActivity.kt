package com.github.gunin_igor75.task_list.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.databinding.ActivityPurchaseItemBinding
import com.github.gunin_igor75.task_list.domain.pojo.Purchase.Companion.NOTING_VALUE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PurchaseItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseItemBinding
    private lateinit var tilName: TextInputLayout
    private lateinit var edName: TextInputEditText
    private lateinit var tilount: TextInputLayout
    private lateinit var edCount: TextInputEditText
    private lateinit var btSave: Button
    private lateinit var viewModel: PurchaseItemViewModel
    private var typeScreen = UNKNOWN_TYPE
    private var purchaseId = NOTING_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseIntent()
        binding = ActivityPurchaseItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        viewModel = ViewModelProvider(this)[PurchaseItemViewModel::class.java]

        launchMode()
        observeViewModel()
        addTextChangeListeners()
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

    private fun initViews() {
        tilName = binding.tilName
        edName = binding.edName
        tilount = binding.tilCount
        edCount = binding.edCount
        btSave = binding.btSave
    }

    private fun launchMode() {
        when (typeScreen) {
            MODE_ADD -> launchAddMode();
            MODE_UPDATE -> launchUpdateMode()
        }
    }

    private fun launchUpdateMode() {
        viewModel.getPurchase(purchaseId)
        viewModel.purchase.observe(this){
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        btSave.setOnClickListener {
            viewModel.updatePurchase(edName.text?.toString(), edCount.text?.toString())
        }
    }

    private fun launchAddMode() {
       btSave.setOnClickListener {
           viewModel.addPurchase(edName.text?.toString(), edCount.text?.toString())
       }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this){
            val message = if (it) getString(R.string.error_name) else null
            tilName.error = message
        }

        viewModel.errorInputCount.observe(this){
            val message = if (it) getString(R.string.error_count) else null
            tilount.error = message
        }

        viewModel.closeScreen.observe(this){
            finish()
        }
    }

    private fun addTextChangeListeners() {
        edName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        edCount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
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
}