package com.sinoptik_.ru1.v2_mutex_in_processor

import com.sinoptik_.ru1.save_prev.EventProcessor2.jobs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


object EventProcessor {

    //chF->mF
//--------------------------------------------------------------
    private val _event = MutableSharedFlow<EventType>()
    val event = _event.asSharedFlow()

    private var countChild = 4
    private var countChildCompletedLoading = 0


    private val _eventStart = MutableSharedFlow<EventTypeStart>()
    val eventStart = _eventStart.asSharedFlow()

    val mutex = Mutex()

    suspend fun childLoadEnd() {
        println("countChildCompletedLoading = $countChildCompletedLoading")
        mutex.withLock {
            countChildCompletedLoading++
        }
        if (countChildCompletedLoading == countChild) {
            println("All jobs completed successfully")
            _event.emit(EventType.SuccessEvent)
            countChildCompletedLoading = 0
        }
    }

    suspend fun sendStart() {
        if (!true) {
            _event.emit(EventType.NoInternetEvent)
        } else {
            _eventStart.emit(EventTypeStart.EventStart)
            _event.emit(EventType.LoadingEvent)
        }
    }
}

sealed interface EventTypeStart {
    object EventStart : EventTypeStart
}


sealed interface EventType {
    object NoInternetEvent : EventType
    object LoadingEvent : EventType
    object SuccessEvent : EventType
}
