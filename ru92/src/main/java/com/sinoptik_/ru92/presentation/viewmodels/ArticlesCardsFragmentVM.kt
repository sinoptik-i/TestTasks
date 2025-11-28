package com.sinoptik_.ru92.presentation.viewmodels

import com.sinoptik_.ru92.data.model.Article
import com.sinoptik_.ru92.domain.usecase.GetArticleByIdUC
import kotlinx.coroutines.flow.MutableStateFlow

class ArticlesCardsFragmentVM : BaseLoadingVM<Int, Article?>(GetArticleByIdUC()) {

    override val _state = MutableStateFlow<LoadState<Article?>>(LoadState.Success(null))
    override var input: Int = -1

    fun loadArticles(id: Int) {
        input = id
        loadData()
    }
}
