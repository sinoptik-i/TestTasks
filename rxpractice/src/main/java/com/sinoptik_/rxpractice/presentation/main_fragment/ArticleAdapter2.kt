package com.sinoptik_.rxpractice.presentation.main_fragment

import com.sinoptik_.empracticelibrary.data.article.model.Article
import com.sinoptik_.empracticelibrary.databinding.TextItemBinding
import com.sinoptik_.empracticelibrary.presentation.adapters.BaseListAdapter

class ArticleAdapter2(
    onItemClick: (Article, Int) -> Unit = { _, _ -> }
) : BaseListAdapter<Article, TextItemBinding>(
    TextItemBinding::inflate,
    onItemClick
) {
    override fun bind(
        binding: TextItemBinding,
        item: Article,
        position: Int
    ) {
        binding.apply {
            textItem.text = item.title
            root.setOnClickListener {
                onItemClick(item, position)
            }
        }
    }
}
