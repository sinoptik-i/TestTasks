package com.sinoptik_.rxpractice.presentation.main_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sinoptik_.empracticelibrary.data.model.Article
import com.sinoptik_.empracticelibrary.databinding.ItemArticleBinding

class MyAdapter(
    private val onItemClick: (Int) -> Unit = {}
) : ListAdapter<Article, MyAdapter.ViewHolder>(ArticleDiffCallback()) {


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
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                articleTitle.text = article.title
                root.setOnClickListener {
                    onItemClick(bindingAdapterPosition)
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
