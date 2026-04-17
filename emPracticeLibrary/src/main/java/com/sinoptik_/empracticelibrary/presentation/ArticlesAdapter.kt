package com.sinoptik_.empracticelibrary.presentation

import com.sinoptik_.empracticelibrary.data.article.model.Article
import com.sinoptik_.empracticelibrary.databinding.TextItemBinding
import com.sinoptik_.empracticelibrary.presentation.adapters.BaseListAdapter


class ArticlesAdapter(
    onItemClick: (Article, Int) -> Unit = { _, _,->}
) : BaseListAdapter<Article, TextItemBinding>(
    TextItemBinding::inflate,
    onItemClick
) {
    override fun bind(
        binding: TextItemBinding,
        item: Article,
        position: Int
    ) {
        binding.textItem.text = item.title
    }
}
