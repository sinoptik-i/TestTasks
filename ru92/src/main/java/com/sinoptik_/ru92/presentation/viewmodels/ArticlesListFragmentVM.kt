package com.sinoptik_.ru92.presentation.viewmodels

import com.sinoptik_.empracticelibrary.data.article.model.Article
import com.sinoptik_.empracticelibrary.domain.LoadDataUseCase
import com.sinoptik_.empracticelibrary.presentation.LoadState
import com.sinoptik_.empracticelibrary.presentation.viewmodels.BaseLoadingVM

import kotlinx.coroutines.flow.MutableStateFlow


class ArticlesListFragmentVM(useCase: LoadDataUseCase<Unit, List<Article>>) :
    BaseLoadingVM<Unit, List<Article>>(useCase) {

    override val _state =
        MutableStateFlow<LoadState<List<Article>?>>(LoadState.Success(emptyList()))

    override val input: Unit get() = Unit

    init {
        loadData()
    }
}
