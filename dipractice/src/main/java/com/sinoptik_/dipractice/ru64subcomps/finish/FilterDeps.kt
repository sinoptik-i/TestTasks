package com.sinoptik_.dipractice.ru64subcomps.finish

import android.content.Context
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment1
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment2
import dagger.Component

@Component(modules = [AnyModule::class])
abstract class FiltersComponentV3 {
    companion object {
        @Suppress("MemberNameEqualsClassName")
        @Volatile
        private var filtersComponentV3: FiltersComponentV3? = null

        @Synchronized
        fun init(context: Context): FiltersComponentV3 {
            if (filtersComponentV3 == null) {
                filtersComponentV3 = DaggerFiltersComponentV3
                    .builder()
                    .build()
            }
            return filtersComponentV3!!
        }
    }

    abstract fun getApi(): Api
    abstract fun getApi2(): Api2
}

@Component(dependencies = [FiltersComponentV3::class])
abstract class Fragment1ComponentV3 {
    internal abstract fun injectFragment(filterFragment: Fragment1)

    @Component.Factory
    interface Factory {
        fun create(filtersComponentV3: FiltersComponentV3): Fragment1ComponentV3
    }
}

@Component(dependencies = [FiltersComponentV3::class])
abstract class Fragment2ComponentV3 {
    internal abstract fun injectFragment2(filterFragment: Fragment2)

    @Component.Factory
    interface Factory {
        fun create(filtersComponentV3: FiltersComponentV3): Fragment2ComponentV3
    }
}


