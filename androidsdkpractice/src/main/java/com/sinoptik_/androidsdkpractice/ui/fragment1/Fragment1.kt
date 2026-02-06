package com.sinoptik_.androidsdkpractice.ui.fragment1

import com.sinoptik_.androidsdkpractice.MainActivity
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.empracticelibrary.presentation.fragments.TwoButtonsFragment

class Fragment1 : TwoButtonsFragment() {
    override val num = 1
    override fun onForward() {
        (activity as MainActivity).router.navigate(Route.Fragment2)
    }

    override fun onBack() {
        (activity as MainActivity).router.navigate(Route.Fragment3)
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
