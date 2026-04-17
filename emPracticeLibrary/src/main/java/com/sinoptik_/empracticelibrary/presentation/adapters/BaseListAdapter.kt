package com.sinoptik_.empracticelibrary.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sinoptik_.empracticelibrary.data.article.model.Article

abstract class BaseListAdapter<T : Any,  VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    protected val onItemClick: (item: T, position: Int) -> Unit = { _, _ -> }
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<VB>>(BaseDiffCallback<T>()) {

    //    var onItemClick: ((T) -> Unit)? = null
    //   abstract fun onItemClick(item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = getItem(position)
        bind(holder.binding, item, position)
        holder.itemView.setOnClickListener {
            onItemClick(item, position )

//            onItemClick?.invoke(item)
        }
    }


    abstract fun bind(binding: VB, item: T, position: Int)

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)


    private class BaseDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    }
}
