package com.sinoptik_.dipractice.ru64subcomps.finish

import android.content.Context
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment1
import com.sinoptik_.dipractice.ru64subcomps.fragments.Fragment2
import com.sinoptik_.dipractice.ru64subcomps.start.DaggerFiltersComponent
import dagger.Binds

import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

interface Api
interface Api2

class AnyApi @Inject constructor() : Api
class AnyApi2 @Inject constructor() : Api2

@Module
abstract class AnyModule {

    @Binds
    abstract fun bindApi(anyApi: AnyApi): Api

    @Binds
    abstract fun bindApi2(anyApi2: AnyApi2): Api2
}


@Component(
    modules = [SubcomponentsModule::class],
)
abstract class FiltersComponentV2 {

    companion object {
        @Suppress("MemberNameEqualsClassName")
        @Volatile
        private var filtersComponentV2: FiltersComponentV2? = null

        @Synchronized
        fun init(context: Context): FiltersComponentV2 {
            if (filtersComponentV2 == null) {
                filtersComponentV2 = DaggerFiltersComponentV2
                    .builder()
                    .build()
            }
            return filtersComponentV2!!
        }
    }

    abstract fun fragment1ComponentFactory(): Fragment1Component.Factory
    abstract fun fragment2ComponentFactory(): Fragment2Component.Factory

}

@Subcomponent
abstract class Fragment1Component {
    internal abstract fun injectFragment1(filterFragment: Fragment1)

    @Subcomponent.Factory
    interface Factory {
        fun create(): Fragment1Component
    }

}

@Subcomponent
abstract class Fragment2Component {
    internal abstract fun injectFragment2(filterFragment: Fragment2)

    @Subcomponent.Factory
    interface Factory {
        fun create(): Fragment2Component
    }
}

@Module(
    subcomponents = [
        Fragment1Component::class,
        Fragment2Component::class
    ]
)
class SubcomponentsModule