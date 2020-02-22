package com.example.movievm

import android.content.Context
import android.util.Log
import androidx.lifecycle.*

class MovieListViewModel(context: Context) : ViewModel(),BaseStateViewModel {
    var liveData: MutableLiveData<Event<Result>> = MutableLiveData<Event<Result>>()
    var liveDataState:MutableLiveData<State> = MutableLiveData()
    var page:Int = 1
    lateinit var s:LiveData<Event<Result>>
    override fun ContentState(): State {
        return liveDataState.value!!
    }

    val repository = Repository(context)
    init {
        detectState()
         s = Transformations.map(repository.apiRepo.loadMoviesLiveData) {
             when(it.status) {
                 Status.SUCCESS->repository.roomRepo.saveAll(it.data?.list as ArrayList<Movie>)
             }
             it
         }
        loadingMovies(page)

//        repository.apiRepo.loadMoviesLiveData.observe(this, Observer {
//            liveData.value = it
//        })
    }
    fun loadingMovies(page:Int){
        repository.apiRepo.getMovies(page)
        Thread.sleep(200)
    }
    fun detectState(){
        Transformations.map(liveData) {
            liveData.postValue(it)
        }
    }
}
