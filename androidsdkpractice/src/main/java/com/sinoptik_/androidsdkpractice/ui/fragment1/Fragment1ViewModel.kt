package com.sinoptik_.androidsdkpractice.ui.fragment1

import androidx.lifecycle.ViewModel
import com.sinoptik_.empracticelibrary.data.location.LocationStorage
import com.sinoptik_.empracticelibrary.data.location.model.LocationState
import kotlinx.coroutines.flow.StateFlow

class Fragment1ViewModel : ViewModel() {
    val locationFlow: StateFlow<LocationState?> = LocationStorage.location
}