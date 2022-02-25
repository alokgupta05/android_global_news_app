package com.personal.paginglibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.personal.paginglibrary.repository.NewsRepo
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val repository: NewsRepo
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }

}
