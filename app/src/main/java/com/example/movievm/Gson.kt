package com.example.movievm

import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GSON {
    @GET("movie?api_key=4b495c719636e84e8c536e9dd58865c0&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&")
    fun getAll(@Query("page")page:Int): Deferred<Response<Result>>
}