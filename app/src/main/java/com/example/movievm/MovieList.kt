package com.example.movievm

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_list_fragment.view.*
import java.lang.reflect.Array.set


class MovieList() : BaseFragment() {

    companion object {
        fun newInstance() = MovieList()
    }
    private lateinit var viewModel: MovieListViewModel
    private lateinit var recyclerView:RecyclerView
    private var adapter:Adapter = Adapter()
    private var page:Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list_fragment, container, false)
        adapter.action = {
            page++
            viewModel.loadingMovies(page)
        }
        adapter.like = {
            viewModel.repository.roomRepo
        }
        view.refresh.setOnRefreshListener{
            viewModel.loadingMovies(1)
        }
        recyclerView =view.recyclerView
        recyclerView.adapter = adapter
        adapter.movingFragment.observe(this, Observer {
            hide()
            val detailFragment = MainActivity.detail
            val movie = adapter.list.get(adapter.movingFragment.value!!)
            Log.i("MovieList",movie.liked.toString())
            if(movie.liked==null){
                movie.liked = false
            }
            Log.i("MovieList1",movie.liked.toString())

            detailFragment.arguments = bundleOf(Pair("movie_details",movie))
            detailFragment.show()
        })
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = MyViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this,factory).get(MovieListViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.repository.roomRepo.liveData.observe(this, Observer {
            adapter.list.addAll(it as ArrayList<Movie>)
            adapter.notifyDataSetChanged()
        })
        viewModel.s.observe(this, Observer {
            when(it.status){
                Status.ERROR->{view?.refresh?.isRefreshing = false;viewModel.repository.roomRepo.getAll();}
                Status.LOADING->{view?.refresh?.isRefreshing = true}
                Status.SUCCESS->{view?.refresh?.isRefreshing = false
                    adapter.list.addAll(it.data?.list as ArrayList<Movie>)
                    for(i in adapter.list.size-20 until adapter.list.size){
                        viewModel.repository.roomRepo.getById(adapter.list.get(i).id ,{ movie ->
                            adapter.list.set(i,movie)
                        },{
                            adapter.notifyItemChanged(i)
                        })
                }
                }
            }
            Log.i("Movielist","changed")
        })

    }
    override var fragment: Fragment
        get() = this
        set(value) {}
}
