package com.sinoptik_.ru92.presentation.list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sinoptik_.ru92.data.model.Article
import com.sinoptik_.ru92.databinding.ItemArticleBinding

class ArticlesAdapter(
    private val onItemClick: (Article) -> Unit = {}
) : ListAdapter<Article, ArticlesAdapter.ViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemArticleBinding,
        private val onItemClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                articleTitle.text = article.title
                root.setOnClickListener {
                    onItemClick(article)
                }
            }
        }
    }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}