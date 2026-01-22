package com.sinoptik_.ru1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class StartWorkClass {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        scope.launch {
            EventProcessor.event.collect { event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: EventType) {
      //  println("${event.javaClass.simpleName}")
                when (event) {
                    EventType.LoadingEvent -> {
                        println("${event.javaClass.simpleName}")
                        countChildCompletedLoading=0
                    }

                    EventType.NoInternetEvent -> {
                        println("${event.javaClass.simpleName}")
                    }

                    EventType.SuccessEvent -> {
                        scope.launch {
                            childLoadEnd()
                        }
                        println("${event.javaClass.simpleName}")

                    }
                }
    }

    //to vm
    //---------------------------
    private var countChild = 4
    private var countChildCompletedLoading = 0

    val mutex = Mutex()
    suspend fun childLoadEnd() {
      //  println("countChildCompletedLoading = $countChildCompletedLoading")
        mutex.withLock {
            countChildCompletedLoading++
        }
        if (countChildCompletedLoading == countChild) {
            println("All jobs completed successfully")
         //   _event.emit(EventType.SuccessEvent)
            countChildCompletedLoading = 0
        }
    }


    // Очистка ресурсов
    fun cleanup() {
        scope.cancel()
    }
}


open class Child {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        scope.launch {
            EventProcessor.eventStart.collect { eventStart ->
                setupEventListeners()
            }
        }
    }

/*
    private fun handleEventStart(eventStart: EventTypeStart) {
        when (eventStart) {
            is EventTypeStart.EventStart -> {
//                println("Begin Start... (4)")
                scope.launch {
                    loadData()
                }
            }
        }
    }
*/

    fun setupEventListeners() {
        scope.launch(
            Dispatchers.IO
        ) {
            launch {
                loadData()
            }.join()
            EventProcessor.send(EventType.SuccessEvent)
        }
    }

    suspend fun loadData() {
        println("${this.javaClass.simpleName} start loading")
        val time = (1000..3000).random().toLong()
        delay(time)
        if (time > 2000) {
            println("time: $time")
            //   throw Exception("loadData Exception")
        }
        println("${this.javaClass.simpleName} loaded")
    }
}

class SubChild1 : Child()
class SubChild2 : Child()
class SubChild3 : Child()
class SubChild4 : Child()

fun ololo(): Boolean {
    println("ololo")
    return true
}

suspend fun main() {

    if (ololo()){
        println("pupupu")
    }


    runBlocking {
        val startWorkClass = StartWorkClass()
        val subChild1 = SubChild1()
        val subChild2 = SubChild2()
        val subChild3 = SubChild3()
        val subChild4 = SubChild4()

        EventProcessor.sendStart()

        delay(20000)
    }
}
/*
//    @Synchronized
LoadingEvent
LazyStandaloneCoroutine{New}@2d782a8d added
LazyStandaloneCoroutine{New}@795fbc00 added
LazyStandaloneCoroutine{New}@178cd36c added
LazyStandaloneCoroutine{New}@75f341d7 added
SubChild3 start loading
SubChild2 start loading
SubChild1 start loading
SubChild4 start loading
SubChild4 loaded
SubChild2 loaded
SubChild3 loaded
time: 2190
SubChild1 loaded
All jobs completed successfully
SuccessEvent
*/
