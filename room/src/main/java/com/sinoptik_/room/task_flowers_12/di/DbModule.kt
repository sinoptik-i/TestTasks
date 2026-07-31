package com.sinoptik_.room.task_flowers_12.di

import android.app.Application
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
import javax.inject.Provider
import javax.inject.Singleton
/**/
@Module
abstract class DbModule {

    companion object {

        @Provides
        @Singleton
        fun provideScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        @Provides
        @Singleton
        fun provideDb(
            context: Context,
            scope: CoroutineScope,
            dbProvider: Provider<FlowersDb>
        ) = FlowersDb.create(context, scope, dbProvider)


        @Provides
        @Singleton
        fun provideDao(db: FlowersDb) = db.dao()

    }

/*    @Binds
    @Singleton
    abstract fun bindContext(app: Application): Context*/

    @Binds
    @Singleton
    abstract fun bindRepo(impl: FlowersRepositoryImpl): FlowersRepository
}