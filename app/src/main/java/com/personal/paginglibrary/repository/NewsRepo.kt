package com.personal.paginglibrary.repository

import com.personal.paginglibrary.model.Article
import com.personal.paginglibrary.model.NewsApiResponse
import com.personal.paginglibrary.model.QueryPath
import com.personal.paginglibrary.network.OpenNewsAPI
import com.personal.paginglibrary.util.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import java.lang.RuntimeException
import javax.inject.Inject

class NewsRepo @Inject constructor(private val openNewsAPI: OpenNewsAPI) {

    suspend fun fetchNews(): Flow<Result<List<Article>>> {
        val queryPath = QueryPath("tata", apikey = AppConstant.API_KEY)
        return flow {
            emit(
                Result.success(
                    openNewsAPI.getResponse(
                        "everything",
                        queryPath.searchTitle,
                        queryPath.page,
                        queryPath.apikey
                    ).articles
                )
            )
        }.catch { exception ->
            emit(Result.failure(RuntimeException(exception.message)));
        }
    }
}


