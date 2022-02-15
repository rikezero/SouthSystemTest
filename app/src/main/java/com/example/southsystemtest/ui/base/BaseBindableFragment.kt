package com.example.southsystemtest.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.southsystemtest.utils.Inflate

abstract class BaseBindableFragment<Binding : ViewBinding>(
    private val inflate: Inflate<Binding>
) : Fragment() {

    lateinit var _binding: Binding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }
}
