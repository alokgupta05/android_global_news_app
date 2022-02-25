package com.personal.paginglibrary.viewmodel

import androidx.lifecycle.*
import com.personal.paginglibrary.model.Article
import com.personal.paginglibrary.model.NewsApiResponse
import com.personal.paginglibrary.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newRepo: NewsRepo) : ViewModel(){

    val loader = MutableLiveData<Boolean>()

    val newsListLiveData = liveData{
        loader.postValue(true)
        emitSource(newRepo.fetchNews()
            .onEach {
                loader.postValue(false)
            }.flatMapMerge{
                flow{
                    it.getOrNull()?.let { it1 -> emit(it1.articles) }
                }
            }
            .asLiveData())
    }

}