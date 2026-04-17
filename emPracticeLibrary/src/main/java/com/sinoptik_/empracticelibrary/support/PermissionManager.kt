package com.sinoptik_.empracticelibrary.support

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

//adb shell pm reset-permissions Ваш.Package.Name
class PermissionManager(fragment: Fragment) {

    // Группируем пермишны в массивы для удобства
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    private val notificationPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyArray()
        }

    // Универсальный лаунчер для любых групп
    private val launcher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val anyGranted = permissions.values.any { it }
        if (anyGranted) {
            onSuccess?.invoke()
        } else {
            onDenied?.invoke("")
        }
    }

   /* fun requestAll() {
        val list = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        launcher.launch(list.toTypedArray())
    }*/

    fun requestNotifications() {
        if (notificationPermission.isNotEmpty()) {
            launcher.launch(notificationPermission)
        } else {
            onSuccess?.invoke()
        }
    }


    // 1. Сначала только "передние" разрешения
    private val foregroundPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // 2. Лаунчер для обычных разрешений
    private val foregroundLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineGranted || coarseGranted) {
            // Передний план дали! Теперь можно просить фон (только на Android 10+)
            requestBackgroundLocation()
        } else {
            onDenied?.invoke("Нужны базовые разрешения для работы")
        }
    }

    // 3. Лаунчер специально для фона (Android 10+)
    private val backgroundLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onSuccess?.invoke()
        } else {
            onDenied?.invoke("Фоновый доступ не разрешен. Воркер может работать нестабильно")
        }
    }

    var onSuccess: (() -> Unit)? = null
    var onDenied: ((String) -> Unit)? = null

    fun requestLocation() {
        foregroundLauncher.launch(foregroundPermissions)
    }

    private fun requestBackgroundLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Запрашиваем ОТДЕЛЬНО
            backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        } else {
            // На старых Android (9 и ниже) разрешение дается автоматически с обычным
            onSuccess?.invoke()
        }
    }

}
