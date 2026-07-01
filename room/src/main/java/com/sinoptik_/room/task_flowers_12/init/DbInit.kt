package com.sinoptik_.room.task_flowers_12.init

import kotlinx.coroutines.CompletableDeferred

object DbInit{
    val isReady= CompletableDeferred<Boolean>()
}