package com.example.southsystemtest.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.southsystemtest.R
import com.example.southsystemtest.databinding.HomeActivityBinding
import com.example.southsystemtest.ui.base.BaseBindableActivity

class HomeActivity : BaseBindableActivity<HomeActivityBinding>() {

    override fun getViewBinding() = HomeActivityBinding.inflate(layoutInflater)

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setGraph()
    }

    private fun setGraph() {
        binding.run {
            findNavController(activityHost.id).setGraph(R.navigation.events_graph)
        }
    }

}