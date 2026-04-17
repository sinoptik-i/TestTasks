package com.sinoptik_.dipractice.ru64subcomps.start

import android.content.Context
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment1
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment2
import dagger.Component


@Component
abstract class FiltersComponent {

    companion object {

        @Suppress("MemberNameEqualsClassName")
        @Volatile
        private var filtersComponent: FiltersComponent? = null

        @Synchronized
        fun init(context: Context): FiltersComponent {
            if (filtersComponent == null) {
                filtersComponent = DaggerFiltersComponent
                    .builder()
                    .build()
            }
            return filtersComponent!!
        }
    }

    internal abstract fun injectFragment1(filterFragment: Fragment1)
    internal abstract fun injectFragment2(filterFragment: Fragment2)
}
