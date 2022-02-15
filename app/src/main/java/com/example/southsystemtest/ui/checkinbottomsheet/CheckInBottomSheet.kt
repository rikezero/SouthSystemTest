package com.example.southsystemtest.ui.checkinbottomsheet

import android.os.Bundle
import android.view.View
import com.example.southsystemtest.databinding.CheckinBottomSheetBinding
import com.example.southsystemtest.ui.base.BaseBottomFragment

class CheckInBottomSheet(
    private val onClickContinue: ((String,String) -> Unit)?
): BaseBottomFragment<CheckinBottomSheetBinding>(CheckinBottomSheetBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setListeners()
    }

    private fun setView() {
        binding.nameField.editText?.requestFocus()
    }

    private fun setListeners() {
        binding.run {
            checkinConfirm.setOnClickListener {
                onClickContinue?.invoke(
                    nameField.editText?.text.toString(),
                    emailField.editText?.text.toString()
                )
                dismiss()
            }
        }
    }
}