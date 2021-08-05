package com.mx.fintesimal.web

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by jduranpc on 8/18/17.
 * Modified by ebonequipc on 8/2/18.
 */
object SentinelAwsServiceGenerator {

    private var apiBaseUrl = "https://fintecimal-test.herokuapp.com/api/interview"
    private var cacheSize = 10 * 1024 * 1024 // 10 MB
    private val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    private var builder: Retrofit.Builder = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create()
            ))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(apiBaseUrl)

    var client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (true) {
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build()
                } else {
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                }
                chain.proceed(request)
            }
            .build()!!



    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }
}