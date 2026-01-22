package com.sinoptik_.empracticelibrary.presentation

sealed class LoadState<out T> {
    object Loading : LoadState<Nothing>()
    data class Success<T>(val data: T) : LoadState<T>()
    data class Error(val throwable: Throwable): LoadState<Nothing>()
}