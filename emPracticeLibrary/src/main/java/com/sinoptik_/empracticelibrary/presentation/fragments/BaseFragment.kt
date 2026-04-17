package com.sinoptik_.empracticelibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding, DataSource> : Fragment() {

    //binding && UI
//------------------------------------------------------------------------------------
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        return _binding!!.root
    }

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

   // abstract fun onSuccess(data: DataSource)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}