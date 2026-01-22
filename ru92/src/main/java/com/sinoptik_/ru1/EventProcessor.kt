package com.sinoptik_.ru1

import com.sinoptik_.ru1.save_prev.EventProcessor2
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

    suspend fun send(event: EventType) {
        _event.emit(event)
    }
    private val _eventStart = MutableSharedFlow<EventTypeStart>()
    val eventStart = _eventStart.asSharedFlow()

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
