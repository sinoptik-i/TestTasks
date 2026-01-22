package com.sinoptik_.rxpractice.presentation.main_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sinoptik_.empracticelibrary.data.model.Article
import com.sinoptik_.empracticelibrary.databinding.ItemArticleBinding
import com.sinoptik_.empracticelibrary.presentation.ArticlesAdapter
import com.sinoptik_.rxpractice.domain.RemoteDataUC
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val remoteDataUC = RemoteDataUC()
    private val _subject = PublishSubject.create<Int>()

    val events = _subject.hide().map {
        it.toString()
    }

    fun onItemClick(num: Int) {
        _subject.onNext(num)
    }

    fun timer() = Observable.interval(1, TimeUnit.SECONDS)

    fun getItems() = remoteDataUC.execute()
}


