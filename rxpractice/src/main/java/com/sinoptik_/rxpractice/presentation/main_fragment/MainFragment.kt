package com.sinoptik_.rxpractice.presentation.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.sinoptik_.rxpractice.databinding.FragmentMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

const val TAG = "MAIN_FRAGMENT"

class MainFragment : Fragment() {

    private var disposeTimer: Disposable? = null
    private var disposeET: Disposable? = null
    private var disposeRV: Disposable? = null
    private var disposeRVItemClick: Disposable? = null


    ////    private var binding: FragmentMainBinding? = null
//    private var binding: FragmentMainBinding?=null
//        get() = binding ?: throw IllegalStateException("Binding is null")
//        set(value) {
//
//        }
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")


    private val viewModel: MainViewModel by viewModels()
    private val adapter = MyAdapter { num ->
        viewModel.onItemClick(num)
    }

    @SuppressLint("CheckResult")
    private fun subscribes() {
        disposeET = binding.inputText.textChanges()
            .skip(1)
            .debounce(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e(TAG, "2 ${Thread.currentThread().name}")
                },
                {
                    Log.e(TAG, it.toString())
                })
        disposeTimer = viewModel.timer()
//            .map {
//                Log.e(TAG, "0 ${Thread.currentThread().name}")
//                it
//            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    Log.e(TAG, "1 ${Thread.currentThread().name}")
                    binding.titleText.text = it.toString()
                },
            )

        binding.recyclerView.apply {
            adapter = this@MainFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }


        //?
        binding.actionButton.setOnClickListener {
            disposeRV?.dispose()
            disposeRV = viewModel.getItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        adapter.submitList(it)
                    }
                )
        }

        disposeRVItemClick = viewModel.events
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                },
                {
                    Log.e(TAG, it.toString())
                }
            )
    }


    companion object {
        fun newInstance() = MainFragment()
    }


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        subscribes()
        return _binding!!.root
    }

    override fun onDestroyView() {
        disposeTimer?.dispose()
        disposeET?.dispose()
        disposeRV?.dispose()
        disposeRVItemClick?.dispose()

        super.onDestroyView()
    }
}