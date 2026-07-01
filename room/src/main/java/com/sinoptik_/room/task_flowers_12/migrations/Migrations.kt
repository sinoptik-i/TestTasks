package com.sinoptik_.room.task_flowers_12.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                ALTER TABLE flowers ADD COLUMN country TEXT NOT NULL DEFAULT 'TERRA INCOGNITA'
            """.trimIndent()
        )

        db.execSQL(
            """
                ALTER TABLE bouquets ADD COLUMN decor TEXT NOT NULL DEFAULT 'BOW'
            """.trimIndent()
        )
    }
}