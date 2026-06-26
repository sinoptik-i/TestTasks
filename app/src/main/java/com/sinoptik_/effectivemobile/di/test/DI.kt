package com.sinoptik_.effectivemobile.di.test

import com.sinoptik_.effectivemobile.main_activity.MainActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Qualifier


@Qualifier
annotation class ACar1

@Qualifier
annotation class ACar2

@Qualifier
annotation class ACar3


@Module
abstract class CarModule {

    @Binds
    @ACar1
    abstract fun detCar1(car: Car1): Car

    @Binds
    @ACar2
    abstract fun detCar2(car: Car2): Car

    @Binds
    @ACar3
    abstract fun detCar3(car: Car3): Car
}


@Component(modules = [CarModule::class])
interface CarComponent {

  //  fun inject(ma: MainActivity)
}


