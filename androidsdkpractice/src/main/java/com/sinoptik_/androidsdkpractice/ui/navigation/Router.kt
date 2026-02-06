package com.sinoptik_.androidsdkpractice.ui.navigation

import com.sinoptik_.androidsdkpractice.ui.fragment1.Fragment1
import com.sinoptik_.androidsdkpractice.ui.fragment1.Fragment2
import com.sinoptik_.androidsdkpractice.ui.fragment1.Fragment3

sealed class Route {
    object Fragment1 : Route()
    object Fragment2 : Route()
    object Fragment3 : Route()
}

class Router(private val navigator: Navigator) {

    fun navigate(route: Route) {
        val fragment = when (route) {
            Route.Fragment1 -> Fragment1()
            Route.Fragment2 -> Fragment2()
            Route.Fragment3 -> Fragment3()
        }
        navigator.replace(fragment)
    }

    fun goBack(): Boolean = navigator.goBack()
}