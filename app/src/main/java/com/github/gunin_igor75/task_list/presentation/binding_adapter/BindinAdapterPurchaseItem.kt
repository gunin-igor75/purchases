package com.github.gunin_igor75.task_list.presentation.binding_adapter

import androidx.databinding.BindingAdapter
import com.github.gunin_igor75.task_list.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("tIeTNumberAsString")
fun bindingTiEtNumberAsString(textInputEditText: TextInputEditText, number: Int) {
    textInputEditText.setText(number.toString())
}

@BindingAdapter("errorInputName")
fun bindingErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_name)
    } else null
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindingErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_count)
    } else null
    textInputLayout.error = message
}
