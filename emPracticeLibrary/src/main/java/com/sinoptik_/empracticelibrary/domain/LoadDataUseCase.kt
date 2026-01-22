package com.sinoptik_.empracticelibrary.domain

import com.sinoptik_.empracticelibrary.data.ArticleRepository

abstract class LoadDataUseCase<Input, Output> {
    protected val repository = ArticleRepository()

    abstract fun loadData(input: Input): Output
}