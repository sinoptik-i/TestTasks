package com.sinoptik_.room.task_flowers_12

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sinoptik_.room.task_flowers_12.entity.Bouquet
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent
import com.sinoptik_.room.task_flowers_12.entity.Flower
import com.sinoptik_.room.task_flowers_12.init.DbInit
import com.sinoptik_.room.task_flowers_12.init.StartData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Provider


@Database(
    entities = [
        Flower::class,
        Bouquet::class,
        BouquetComponent::class],
    version = 1,
    exportSchema = true
)
abstract class FlowersDb : RoomDatabase() {
    abstract fun dao(): FlowerShopDao

    companion object {
        fun create(
            context: Context,
            scope: CoroutineScope,
            dbProvider: Provider<FlowersDb>
        ): FlowersDb {
            val db = Room.databaseBuilder(
                context = context.applicationContext,
                klass = FlowersDb::class.java,
                name = "flowers_database"
            )
                .addCallback(
                    FlowerShopDatabaseCallback(
                        scope = scope,
                        dbProvider = { dbProvider.get() }
                    )
                )
                .build()

            scope.launch {
                db.query(SimpleSQLiteQuery("SELECT 1")).close()
//                db.compileStatement("SELECT 1").executeInsert()
//                db.compileStatement("SELECT 1").execute()
            }
            return db

        }
    }
}

    class FlowerShopDatabaseCallback(
        private val scope: CoroutineScope,
        private val dbProvider: () -> FlowersDb
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                try {
                    StartData.populateDatabase(dbProvider().dao())
                } finally {
                    DbInit.isReady.complete(true)
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            DbInit.isReady.complete(true)
        }
    }

/*
companion object {
    @Volatile
    private var INSTANCE: FlowersDb? = null
    fun getDatabase(
        context: Context,
        scope: CoroutineScope
    ) = INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
            context = context.applicationContext,
            klass = FlowersDb::class.java,
            name = "flowers_database"
        )
            .addCallback(
                FlowerShopDatabaseCallback(
                    scope,
                    { INSTANCE!! })
            )
            .build()
        INSTANCE = instance
        instance
    }
}*/
