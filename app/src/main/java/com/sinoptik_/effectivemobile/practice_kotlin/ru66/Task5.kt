package com.sinoptik_.effectivemobile.practice_kotlin.ru66

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope

const val TASK5 = "TASK5"

class Task5(context: Context) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun save(scope: CoroutineScope) {
        sharedPref.saveDataAsync(
            data = mapOf(
                "username" to "John Doe",
                "user_id" to 123,
                "is_logged_in" to true,
                "balance" to 999.99f
            ),
            writeMode = WriteMode.APPLY,
            scope = scope,
            onSuccess = {
                Log.i(TASK5, "Данные успешно сохранены")
            },
            onError = { e ->
                Log.e(TASK5, "Ошибка: ${e.message}")
            }
        )
    }

    fun load() {
        val result = sharedPref.getValue<String>("username", null)
        Log.i(TASK5, "Результат: $result")
    }
}