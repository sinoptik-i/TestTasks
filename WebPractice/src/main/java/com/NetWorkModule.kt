package com

import android.content.Context
import com.sinoptik_.ru35.NetworkMonitor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetWorkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(context: Context) = NetworkMonitor(context)

}
