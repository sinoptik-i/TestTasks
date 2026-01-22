package com.sinoptik_.ru92.presentation.articles_cards_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sinoptik_.empracticelibrary.data.model.Article
import com.sinoptik_.empracticelibrary.presentation.fragments.BaseFragment
import com.sinoptik_.ru92.databinding.FragmentArticleDetailBinding
import com.sinoptik_.ru92.presentation.viewmodels.ArticlesCardsFragmentVM

class ArticlesCardsFragment : BaseFragment<FragmentArticleDetailBinding, Int, Article?>() {

    override val viewModel = ArticlesCardsFragmentVM()
    private var articleId: Int = 0

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleDetailBinding {
        return FragmentArticleDetailBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleId = arguments?.getInt("id", -1) ?: -1
        if (articleId > -1) {
            viewModel.loadArticles(articleId)
        }
    }

/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }*/

    override fun loadingBehavior() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.GONE
        binding.tvDescription.visibility = View.GONE
    }

    override fun onSuccess(data: Article?) {
        binding.progressBar.visibility = View.GONE

        if (data != null) {
            binding.tvTitle.text = data.title
            binding.tvDescription.text = data.description
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvDescription.visibility = View.VISIBLE
        } else {
            binding.tvTitle.text = "Не найдена"
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvDescription.visibility = View.GONE
        }
    }
}
