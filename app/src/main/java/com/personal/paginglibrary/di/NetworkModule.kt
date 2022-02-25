package com.personal.paginglibrary.di

import com.google.gson.Gson
import com.personal.paginglibrary.ext.addInterceptorIfDebug
import com.personal.paginglibrary.network.OpenNewsAPI
import com.personal.paginglibrary.util.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenNewsAPI(retrofit: Retrofit): OpenNewsAPI {
        return retrofit.create(OpenNewsAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}
