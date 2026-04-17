package com.sinoptik_.androidsdkpractice.ui.fragment1

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sinoptik_.androidsdkpractice.MainActivity
import com.sinoptik_.androidsdkpractice.R
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.empracticelibrary.presentation.custom_view.ChargingRectView
import com.sinoptik_.empracticelibrary.presentation.fragments.TwoButtonsFragment
import com.sinoptik_.empracticelibrary.support.PermissionManager
import kotlinx.coroutines.launch

class Fragment1 : TwoButtonsFragment() {

    private val viewModel: Fragment1ViewModel by viewModels()
    override val num = 1
    override fun onForward() {
        (activity as MainActivity).router.navigate(Route.Fragment2)
    }

    override fun onBack() {
        (activity as MainActivity).router.navigate(Route.Fragment3)
    }

    private var extraTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCustomCharged()
        initLocationView()


//        binding.extraContentStub.layoutResource = R.layout.charging_rect_view
//        binding.extraContentStub.inflate()
    }

    private fun initPermissions(){
        val permissionManager = PermissionManager(this)
        permissionManager.requestNotifications()
        permissionManager.requestLocation()

            //    permissionManager.requestAll()
    }

    private fun initLocationView() {
        binding.extraContentStub2.layoutResource =
            com.sinoptik_.empracticelibrary.R.layout.dynamic_text_view
        extraTextView = binding.extraContentStub2.inflate() as? TextView
        extraTextView?.text = "Координаты: 0.0, 0.0"
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationFlow.collect { state ->
                    state?.let {
                        extraTextView?.text =
                            "Lat: ${it.latitude}, Long: ${it.longitude}, Time: ${it.timestamp}"
                    }
                }
            }
        }
    }

    private fun initCustomCharged() {
        val myCustomView = ChargingRectView(requireContext())

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            500
        ).apply {
            topMargin = 50
        }

        myCustomView.layoutParams = params
        binding.root.addView(myCustomView)
    }

}

class Fragment2 : TwoButtonsFragment() {
    override val num = 2
    override fun onForward() {
        (activity as MainActivity).router.navigate(Route.Fragment3)
    }

    override fun onBack() {
        (activity as MainActivity).router.navigate(Route.Fragment1)
    }
}

class Fragment3 : TwoButtonsFragment() {
    override val num = 3

    override fun onForward() {
        (activity as MainActivity).router.navigate(Route.Fragment1)
    }

    override fun onBack() {
        (activity as MainActivity).router.navigate(Route.Fragment2)
    }
}
