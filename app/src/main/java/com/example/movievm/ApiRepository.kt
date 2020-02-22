package com.example.movievm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Error

class ApiRepository{
    val apiclient = ApiClient()
    val api = apiclient.getApiClientByCoroutines()?.create(GSON::class.java)
    companion object{
        var itself:ApiRepository? = null
        fun getInstance():ApiRepository{
            if (itself == null){
                itself = ApiRepository()
            }
            return itself!!
        }
    }
    var loadMoviesLiveData: MutableLiveData<Event<Result>> = MutableLiveData<Event<Result>>()
    fun getMovies(page:Int){
        loadMoviesLiveData.value = Event(Status.LOADING,null,null)
        GlobalScope.launch(Dispatchers.IO) {
            try{
            val movies = api?.getAll(page)?.await()
            when(movies?.code()){
                201,200->loadMoviesLiveData.postValue(Event(Status.SUCCESS,movies.body(),null))
                else->loadMoviesLiveData.postValue(Event(Status.ERROR,null,Error(movies?.errorBody().toString())))
            }}
            catch (e:Exception){
                loadMoviesLiveData.postValue(Event(Status.ERROR,null,null))
            }
        }

    }
}