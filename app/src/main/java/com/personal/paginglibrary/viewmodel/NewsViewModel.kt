package com.personal.paginglibrary.viewmodel

import androidx.lifecycle.*
import com.personal.paginglibrary.model.Article
import com.personal.paginglibrary.model.NewsApiResponse
import com.personal.paginglibrary.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.RuntimeException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newRepo: NewsRepo) : ViewModel() {

    val loader = MutableLiveData(false)

    val newsListLiveData = flow<Result<List<Article>>> {
        loader.value = true
        newRepo.fetchNews()
            .onEach {
                loader.value = false
            }
            .collect{
                if(it.isSuccess){
                    val articles = it.getOrNull()?: emptyList()
                    emit(Result.success(articles))
                }else{
                    val articles = it.exceptionOrNull()?: RuntimeException("Something went wrong")
                    emit(Result.failure(articles))
                }
            }
    }

}