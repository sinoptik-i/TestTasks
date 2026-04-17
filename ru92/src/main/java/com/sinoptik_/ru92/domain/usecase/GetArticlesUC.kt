package com.sinoptik_.ru92.domain.usecase

import com.sinoptik_.empracticelibrary.data.article.model.Article
import com.sinoptik_.empracticelibrary.domain.LoadDataUseCase


class GetArticlesUC : LoadDataUseCase<Unit, List<Article>>() {
    override fun loadData(input: Unit): List<Article> = repository.getAllArticles().let {
        it.shuffled()
            .take((1..it.size).random())
    }
}


