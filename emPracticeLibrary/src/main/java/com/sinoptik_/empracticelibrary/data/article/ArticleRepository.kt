package com.sinoptik_.empracticelibrary.data.article

import com.sinoptik_.empracticelibrary.data.article.model.articles

class ArticleRepository {

    fun getAllArticles() = articles

    fun getArticleById(id: Int) = articles.find { it.id == id }
}