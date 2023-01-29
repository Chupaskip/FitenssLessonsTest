package com.example.fitensslessonstest.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FitnessViewModelFactory(
    val app:Application
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LessonsViewModel(app) as T
    }
}