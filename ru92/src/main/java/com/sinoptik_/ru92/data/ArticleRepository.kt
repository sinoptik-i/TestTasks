package com.sinoptik_.ru92.data

import com.sinoptik_.ru92.data.model.articles

class ArticleRepository {

    fun getAllArticles() = articles

    fun getArticleById(id: Int) = articles.find { it.id == id }
}