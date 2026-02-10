package com.sinoptik_.androidsdkpractice.ui.fragment1

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.sinoptik_.androidsdkpractice.MainActivity
import com.sinoptik_.androidsdkpractice.R
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.empracticelibrary.presentation.custom_view.ChargingRectView
import com.sinoptik_.empracticelibrary.presentation.fragments.TwoButtonsFragment

class Fragment1 : TwoButtonsFragment() {
    override val num = 1
    override fun onForward() {
        (activity as MainActivity).router.navigate(Route.Fragment2)
    }

    override fun onBack() {
        (activity as MainActivity).router.navigate(Route.Fragment3)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCustomView = ChargingRectView(requireContext())

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            500
        ).apply {
            topMargin = 50
        }

        myCustomView.layoutParams = params
        binding.root.addView(myCustomView)

//        binding.extraContentStub.layoutResource = R.layout.charging_rect_view
//        binding.extraContentStub.inflate()
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
