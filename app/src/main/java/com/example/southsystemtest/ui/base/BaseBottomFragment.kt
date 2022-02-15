package com.example.southsystemtest.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.southsystemtest.R
import com.example.southsystemtest.utils.Inflate
import com.example.southsystemtest.utils.hideKeyboard
import com.example.southsystemtest.utils.showKeyboard
import com.example.southsystemtest.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomFragment<Binding : ViewBinding>(
    private val inflate: Inflate<Binding>,
    private val isNotHideable: Boolean = false
) : BottomSheetDialogFragment() {

    lateinit var _binding: Binding
    val binding get() = _binding

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (isNotHideable){
            setDialogAsNotHideable(dialog)
        }
        return dialog
    }

    private fun setDialogAsNotHideable(dialog: Dialog) {
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet: FrameLayout? = bottomSheetDialog.findViewById(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet ?: return@setOnShowListener).isHideable = false
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setOnShowListener {
            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet ?: return@setOnShowListener).state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        binding.onBinding()
        return binding.root
    }

    // TOAST METHODS
    fun showToast(string: String) {
        activity?.showToast(string)
    }

    // KEYBOARD METHODS
    fun hideSoftKeyboard() {
        activity?.hideKeyboard()
    }

    fun showSoftKeyBoard() {
        activity?.showKeyboard()
    }

    open fun Binding.onBinding() {}
}