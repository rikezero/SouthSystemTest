package com.example.southsystemtest.ui.base

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel <Intent, State>: ViewModel() {

    protected val state = SingleLiveEvent<State>()
    val uiState: LiveData<State> = state

    abstract fun handle(intent: Intent)
}