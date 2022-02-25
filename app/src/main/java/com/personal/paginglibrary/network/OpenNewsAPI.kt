package com.personal.paginglibrary.network

import com.personal.paginglibrary.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface OpenNewsAPI {

    @GET("/v2/{name}")
    suspend fun getResponse(
        @Path("name") name: String, @Query("q") searcQuery: String,
        //@Query("q") searcQuery: String,  @Query("from") lat: String,
        @Query("page") page: Int, @Query("apiKey") apiKey: String
    ): NewsApiResponse

}
