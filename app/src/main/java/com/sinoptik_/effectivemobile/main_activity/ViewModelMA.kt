package com.sinoptik_.effectivemobile.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinoptik_.effectivemobile.di.App
import com.sinoptik_.effectivemobile.di.AppComponent
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import com.sinoptik_.room.task_flowers_12.test.TestDb
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelMA @Inject constructor(
    private val repo: FlowersRepository
) : ViewModel() {

    fun testFlowersDb() {
        val testDb = TestDb(repo)
        viewModelScope.launch {
            testDb.test()
        }
    }


    //    @Inject
//    @ACar1
//    lateinit var car1: Car
//
//    @Inject
//    @ACar2
//    lateinit var car2: Car
//
//    @Inject
//    @ACar3
//    lateinit var car3: Car

    //    private fun annotationTest() {
//
//        (application as App).carComponent.inject(this)
//        car1.drive()
//        car2.drive()
//        car3.drive()
//    }
}