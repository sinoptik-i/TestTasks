package com.sinoptik_.empracticelibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinoptik_.empracticelibrary.databinding.BaseRecyclerBinding
import com.sinoptik_.empracticelibrary.presentation.adapters.BaseRvAdapter

abstract class BaseRecyclerFragment : BaseFragment<BaseRecyclerBinding, List<String>>() {


   protected val adapter = BaseRvAdapter()


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BaseRecyclerBinding {
        return BaseRecyclerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recycler.apply {
            adapter = this@BaseRecyclerFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

}