package com.sinoptik_.ru92.domain.usecase

import com.sinoptik_.empracticelibrary.data.model.Article
import com.sinoptik_.empracticelibrary.domain.LoadDataUseCase

class GetArticleByIdUC : LoadDataUseCase<Int, Article?>() {
    override fun loadData(input: Int)=repository.getArticleById(input)
}