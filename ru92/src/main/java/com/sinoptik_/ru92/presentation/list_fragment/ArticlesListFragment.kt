package com.sinoptik_.ru92.presentation.list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinoptik_.ru92.R
import com.sinoptik_.ru92.data.model.Article
import com.sinoptik_.ru92.databinding.FragmentListBinding
import com.sinoptik_.ru92.domain.usecase.GetArticlesUC
import com.sinoptik_.ru92.presentation.BaseFragment
import com.sinoptik_.ru92.presentation.viewmodels.ArticlesListFragmentVM


class ArticlesListFragment : BaseFragment<FragmentListBinding, Unit, List<Article>>() {

    private val getArticlesUC = GetArticlesUC()
    override val viewModel = ArticlesListFragmentVM(getArticlesUC)

    private val adapter = ArticlesAdapter { article ->
        var bundle = bundleOf("id" to article.id)
        findNavController().navigate(
            R.id.action_listFragment_to_articleDetailFragment, bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentListBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        subscribeToViewModel()
        viewModel.loadData()
    }

    private fun setupRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = this@ArticlesListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.loadData()
            }
        }
    }


    override fun onSuccess(data: List<Article>) {
        val lm = binding.articlesRecyclerView.layoutManager
        val sst = lm?.onSaveInstanceState()
        adapter.submitList(data) {
            lm?.onRestoreInstanceState(sst)
        }
        binding.swipeRefresh.isRefreshing = false
    }


    override fun loadingBehavior() {
        binding.progressBar.visibility //= View.VISIBLE
        if (!binding.swipeRefresh.isRefreshing)
            View.VISIBLE
        else
            View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

