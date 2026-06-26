package com.sinoptik_.room.task_flowers_12.di

import android.content.Context
import com.sinoptik_.room.task_flowers_12.FlowerShopDao
import com.sinoptik_.room.task_flowers_12.FlowersDb
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import com.sinoptik_.room.task_flowers_12.FlowersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
abstract class DbModule {

    companion object {

        @Provides
        @Singleton
        fun provideScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        @Provides
        @Singleton
        fun provideDao(context: Context, scope: CoroutineScope) = FlowersDb.getDatabase(
            context,
            scope
        ).dao()

    }

    @Binds
    @Singleton
    abstract fun bindRepo(impl: FlowersRepositoryImpl): FlowersRepository
}