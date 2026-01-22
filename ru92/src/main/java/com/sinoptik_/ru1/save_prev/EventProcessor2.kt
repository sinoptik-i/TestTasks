package com.sinoptik_.ru1.save_prev

import com.sinoptik_.ru1.EventType
import com.sinoptik_.ru1.EventTypeStart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object EventProcessor2 {

    //chF->mF
//--------------------------------------------------------------
    private val _event = MutableSharedFlow<EventType>()
    val event = _event.asSharedFlow()

    suspend fun send(event: EventType) {
        _event.emit(event)
    }

    private val _eventStart = MutableSharedFlow<EventTypeStart>()
    val eventStart = _eventStart.asSharedFlow()

    val jobs = mutableListOf<Job>()
    val scope = CoroutineScope(Dispatchers.IO)
    val mutex = Mutex()

    //    @Synchronized
    suspend fun addJob(job: Job) {
        mutex.withLock {
            jobs.add(job)
        }
        println(job.toString() + " added")
        if (jobs.size == 4) {
            scope.launch {
                try {
                    jobs.forEach { it.start() }

                    jobs.joinAll()
                    println("All jobs completed successfully")

                    _event.emit(EventType.SuccessEvent)
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
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

/*sealed interface EventTypeStart {
    object EventStart : EventTypeStart
}


sealed interface EventType {
    object NoInternetEvent : EventType
    object LoadingEvent : EventType
    object SuccessEvent : EventType
}*/
