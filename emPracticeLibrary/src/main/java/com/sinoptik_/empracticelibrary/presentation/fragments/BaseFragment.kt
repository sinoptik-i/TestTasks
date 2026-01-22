package com.sinoptik_.empracticelibrary.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.sinoptik_.empracticelibrary.presentation.LoadState
import com.sinoptik_.empracticelibrary.presentation.viewmodels.BaseLoadingVM
import kotlinx.coroutines.launch


abstract class BaseFragment<VB : ViewBinding, Input, DataSource> : Fragment() {

    //binding && UI
//------------------------------------------------------------------------------------
    protected var _binding: VB? = null
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

    abstract fun onSuccess(data: DataSource)
    abstract fun loadingBehavior()

    // abstract fun showErrorMessage(message:String)
    //VM subscribing  &&DataState logic
//------------------------------------------------------------------------------------
    abstract val viewModel: BaseLoadingVM<Input, DataSource>

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (val current = state) {
                        is LoadState.Loading -> {
                            loadingBehavior()
                        }

                        is LoadState.Success -> {
                            current.data?.let {
                                onSuccess(it)
                            }
                            //binding.progressBar.visibility = View.GONE
                        }

                        is LoadState.Error -> {
                            current.throwable.message?.let {
                                showErrorMessage(it)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    fun showErrorMessage(message: String) {
        val toast = Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}