package com.sinoptik_.ru92.domain.usecase

import com.sinoptik_.ru92.data.model.Article

class GetArticleByIdUC : LoadDataUseCase<Int, Article?>() {
    override fun loadData(input: Int)=repository.getArticleById(input)
}