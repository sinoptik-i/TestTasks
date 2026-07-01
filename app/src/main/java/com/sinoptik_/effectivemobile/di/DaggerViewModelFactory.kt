package com.sinoptik_.effectivemobile.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sinoptik_.effectivemobile.main_activity.ViewModelMA
import com.sinoptik_.room.task_flowers_12.FlowerShopDao
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import com.sinoptik_.room.task_flowers_12.init.DbInit
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactory @Inject constructor(
    private val vmProvider: Provider<ViewModelMA>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMA::class.java)) {
            return vmProvider.get() as T
        }
        throw IllegalArgumentException("Unknown VM: ${modelClass.name}")
    }
}

