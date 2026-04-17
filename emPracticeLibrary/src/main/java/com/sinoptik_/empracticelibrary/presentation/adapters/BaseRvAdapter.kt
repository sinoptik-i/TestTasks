package com.sinoptik_.empracticelibrary.presentation.adapters

import com.sinoptik_.empracticelibrary.databinding.BaseRecyclerBinding
import com.sinoptik_.empracticelibrary.databinding.TextItemBinding
import com.sinoptik_.empracticelibrary.presentation.fragments.BaseRecyclerFragment

class BaseRvAdapter : BaseListAdapter<String, TextItemBinding>(TextItemBinding::inflate) {
    override fun bind(
        binding: TextItemBinding,
        item: String,
        position: Int
    ) {
        binding.apply {
            textItem.text = item
        }
    }
}