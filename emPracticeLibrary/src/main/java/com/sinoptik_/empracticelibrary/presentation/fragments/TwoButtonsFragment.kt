package com.sinoptik_.empracticelibrary.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.sinoptik_.empracticelibrary.databinding.TwoButtonsBinding

abstract class TwoButtonsFragment() :
    BindingFragment<TwoButtonsBinding>(TwoButtonsBinding::inflate) {

    abstract val num: Int
    abstract fun onForward()
    abstract fun onBack()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.screenNum.text = "Screen $num"

        binding.buttonBack.setOnClickListener {
            onBack()
        }
        binding.buttonForward.setOnClickListener {
            onForward()
        }
    }
}