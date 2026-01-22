package com.sinoptik_.empracticelibrary.data

import com.sinoptik_.empracticelibrary.data.model.articles

class ArticleRepository {

    fun getAllArticles() = articles

    fun getArticleById(id: Int) = articles.find { it.id == id }
}