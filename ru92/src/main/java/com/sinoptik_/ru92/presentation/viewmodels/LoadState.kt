package com.sinoptik_.ru92.presentation.viewmodels

sealed class LoadState<out T> {
    object Loading : LoadState<Nothing>()
    data class Success<T>(val data: T) : LoadState<T>()
}