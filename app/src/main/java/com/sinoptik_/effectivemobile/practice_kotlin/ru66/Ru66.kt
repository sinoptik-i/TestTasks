package com.sinoptik_.effectivemobile.practice_kotlin.ru66

import android.content.SharedPreferences
import android.util.Log.e
import kotlinx.coroutines.*

enum class WriteMode {
    APPLY,
    COMMIT
}

fun SharedPreferences.saveDataAsync(
    data: Map<String, Any>,
    writeMode: WriteMode = WriteMode.APPLY,
    scope: CoroutineScope,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val editor = this.edit()
    val handler = CoroutineExceptionHandler { _, exception ->
        e(TASK5, "${exception.message}")
    }

    scope.launch(Dispatchers.IO + handler) {
        try {
            data.forEach { (key, value) ->
                when (value) {
                    is String -> editor.putString(key, value)
                    is Int -> editor.putInt(key, value)
                    is Long -> editor.putLong(key, value)
                    is Float -> editor.putFloat(key, value)
                    is Boolean -> editor.putBoolean(key, value)
                    else -> onError(
                        Exception
                            ("Unsupported data type: ${value::class.simpleName}")
                    )
                }
            }
            when (writeMode) {
                WriteMode.APPLY -> {
                    editor.apply()
                    onSuccess()
                }

                WriteMode.COMMIT -> {
                    val isSuccess = editor.commit()
                    if (isSuccess) {
                        onSuccess()
                    } else {
                        onError(Exception("Commit operation failed"))
                        return@launch
                    }
                }
            }
        } catch (e: Exception) {
            onError(e)
        }
    }
}

inline fun <reified T> SharedPreferences.getValue(
    key: String,
    defaultValue: T? = null
): T? {
    if (!contains(key)) {
        return defaultValue
    }

    try {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String ?: "") as? T
            Int::class -> getInt(key, defaultValue as? Int ?: 0) as? T
            Long::class -> getLong(key, defaultValue as? Long ?: 0L) as? T
            Float::class -> getFloat(key, defaultValue as? Float ?: 0f) as? T
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as? T
            else -> defaultValue
        }
    } catch (ex: Exception) {
        e(TASK5, "Yеверно указан загружаемый тип данных, ${ex.message}")
        return defaultValue
    }
}