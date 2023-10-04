package com.github.gunin_igor75.task_list.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.domain.pojo.Purchase.Companion.NOTING_VALUE
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity.Companion.MODE
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity.Companion.MODE_ADD
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity.Companion.MODE_UPDATE
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity.Companion.PURCHASE_ID
import com.github.gunin_igor75.task_list.presentation.PurchaseItemActivity.Companion.UNKNOWN_TYPE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PurchaseItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var edName: TextInputEditText
    private lateinit var tilount: TextInputLayout
    private lateinit var edCount: TextInputEditText
    private lateinit var btSave: Button
    private lateinit var viewModel: PurchaseItemViewModel
    private lateinit var onFinishedListener: OnFinishedListener
    private var typeScreen: String = UNKNOWN_TYPE
    private var purchaseId: Int = NOTING_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_purchase_item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishedListener) {
            onFinishedListener = context
        } else {
            throw RuntimeException(
                "Activity mast implement interface PurchaseItemFragment.OnFinishedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PurchaseItemViewModel::class.java]
        initViews(view)
        launchMode()
        observeViewModel()
        addTextChangeListeners()
    }


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(MODE)) {
            throw RuntimeException("Missing type")
        }
        val mode = args.getString(MODE)
        if (mode != MODE_UPDATE && mode != MODE_ADD) {
            throw RuntimeException("Unknown type $mode")
        }
        typeScreen = mode
        if (mode == MODE_UPDATE) {
            if (!args.containsKey(PURCHASE_ID)) {
                throw RuntimeException("purchase id is missing")
            }
            purchaseId = args.getInt(PURCHASE_ID, NOTING_VALUE)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        edName = view.findViewById(R.id.ed_name)
        tilount = view.findViewById(R.id.til_count)
        edCount = view.findViewById(R.id.ed_count)
        btSave = view.findViewById(R.id.bt_save)
    }

    private fun launchMode() {
        when (typeScreen) {
            MODE_ADD -> launchAddMode()
            MODE_UPDATE -> launchUpdateMode()
        }
    }

    private fun launchAddMode() {
        btSave.setOnClickListener {
            viewModel.addPurchase(edName.text?.toString(), edCount.text?.toString())
        }
    }

    private fun launchUpdateMode() {
        viewModel.getPurchase(purchaseId)
        viewModel.purchase.observe(this) {
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        btSave.setOnClickListener {
            viewModel.updatePurchase(edName.text?.toString(), edCount.text?.toString())
        }
    }


    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error_name) else null
            tilName.error = message
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error_count) else null
            tilount.error = message
        }

        viewModel.closeScreen.observe(viewLifecycleOwner) {
            onFinishedListener.onFinished()
        }
    }

    private fun addTextChangeListeners() {
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    interface OnFinishedListener {
        fun onFinished();
    }

    companion object {

        fun newInstanceAddItem(): PurchaseItemFragment {
            return PurchaseItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceUpdateItem(purchaseId: Int): PurchaseItemFragment {
            return PurchaseItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_UPDATE)
                    putInt(PURCHASE_ID, purchaseId)
                }
            }
        }
    }
}