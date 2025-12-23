package com.sinoptik_.ru92.presentation.viewmodels

import android.R.id.input
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinoptik_.ru92.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


abstract class BaseLoadingVM<Input, DataSource>(
    private val useCase: LoadDataUseCase<Input, DataSource>
) : ViewModel() {
    abstract val _state: MutableStateFlow<LoadState<DataSource?>>
    abstract val input: Input
    val state: StateFlow<LoadState<DataSource?>> get() = _state.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _state.value = LoadState.Loading
            try {
                val data= withContext(Dispatchers.IO){
                    useCase.loadData(input)
                }
                _state.update { LoadState.Success(data) }
            } catch (e: Exception) {
                _state.update {
                    LoadState.Error(e) }
            }
        }
    }

}

