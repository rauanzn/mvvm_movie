package com.example.movievm

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class RoomRepository(var context: Context){
    var liveData:MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    companion object{
        var itself:RoomRepository? = null
        fun getInstance(context:Context):RoomRepository{
            if (itself == null){
                itself = RoomRepository(context)
            }
            return itself!!
        }
    }
    fun saveAll(list:ArrayList<Movie>){
        GlobalScope.launch(Dispatchers.IO) {
            for (i in list) {
                if (MovieDatabase.getInstance(context)?.MovieDataDao()?.getElementById(i.id) == null) {
                    i.liked = false
                    MovieDatabase.getInstance(context)?.MovieDataDao()?.insert(i)
                }
                else{
                    MovieDatabase.getInstance(context)?.MovieDataDao()?.changeStat(i)
                }
            }
        }
    }
    fun deleteAll(){
        GlobalScope.launch(Dispatchers.IO) {
            MovieDatabase.getInstance(context)?.MovieDataDao()?.deleteAll()
        }
    }
    fun getAll(){
        GlobalScope.launch(Dispatchers.IO) {
            val a = MovieDatabase.getInstance(context)?.MovieDataDao()?.getAll()
            Log.i("RoomRepository",liveData.value?.size.toString())
            if(liveData.value?.size==null) {
                liveData.postValue(a)
            }
        }
    }
    fun changeStar(id:Int,func:(liked:Boolean)->Unit){
        GlobalScope.launch(Dispatchers.IO) {
            val a = MovieDatabase.getInstance(context)?.MovieDataDao()?.getElementById(id)
            Log.i("RoomRepository",a.toString())
            if(a != null){
                a.liked = !(a.liked)
                MovieDatabase.getInstance(context)?.MovieDataDao()?.changeStat(a)
                func.invoke(a.liked)
            }
        }
    }
    fun getById(id:Int,func:(movie:Movie)->Unit,mainFunc:()->Unit){
        GlobalScope.launch(Dispatchers.IO) {
            val a = MovieDatabase.getInstance(context)?.MovieDataDao()?.getElementById(id)
            if(a != null){
                Log.i("RoomRepository1",a.liked.toString())
                func.invoke(a)
            }
            withContext(Dispatchers.Main){
                mainFunc.invoke()
            }
        }
    }
}