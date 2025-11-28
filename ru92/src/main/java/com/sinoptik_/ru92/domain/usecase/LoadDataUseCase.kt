package com.sinoptik_.ru92.domain.usecase

import com.sinoptik_.ru92.data.ArticleRepository

abstract class LoadDataUseCase<Input, Output> {
    protected val repository = ArticleRepository()

    abstract fun loadData(input: Input): Output
}