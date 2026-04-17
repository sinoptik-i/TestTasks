package com.sinoptik_.empracticelibrary.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true)
    fun goBack(): Boolean
    fun replace(fragment: Fragment)
}


class FragmentNavigator(private val activity: AppCompatActivity, private val containerId: Int) :
    Navigator {

    override fun navigateTo(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(containerId, fragment, fragment::class.java.simpleName)
        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.java.simpleName)
        }
        transaction.commit()
    }

    override fun goBack(): Boolean {
        return if (activity.supportFragmentManager.backStackEntryCount > 0) {
            activity.supportFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }

    override fun replace(fragment: Fragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, fragment::class.java.simpleName)
        transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}