package com.example.movievm

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.MovieHolder>(),OnEndReachCallback {
    var action:(()->Unit)? = null
    var like:(()->RoomRepository)? = null
    override fun onEndReachCallback() {
        action?.invoke()
    }

    var list:ArrayList<Movie> = ArrayList()
    var movingFragment:MutableLiveData<Int> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return MovieHolder(view)
    }
    lateinit var recyclerView: RecyclerView
    lateinit var onEndReachCallback:OnEndReachCallback
    private var mScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount =  mLayoutManager.childCount
            val totalItemCount = mLayoutManager.itemCount
            val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
            Log.i("AdapterPos",pastVisibleItems.toString())
            Log.i("Adaptervisi",visibleItemCount.toString())
            Log.i("AdapterTotal",totalItemCount.toString())
            if (pastVisibleItems + visibleItemCount >= totalItemCount && totalItemCount>=20) {
                onEndReachCallback()
            }
        }}

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w780"+list.get(position).poster_path).into(holder.itemView.poster_path)
        holder.itemView.movie_name.setText(list.get(position).title)
        holder.itemView.rating.setText(list.get(position).vote_average.toString())
        holder.itemView.movie_date.setText(list.get(position).release_date)
        like?.invoke()?.getById(position ,{
            if (it.liked != null) {
                list.set(position, it)
            }},{
        })
        holder.itemView.setOnClickListener{
            movingFragment.value = position
        }
        if(list.get(position).liked == true){
            holder.itemView.item_liked.setImageResource(R.drawable.ic_star_yellow)
        }
        holder.itemView.item_liked.setOnClickListener{
            like?.invoke()?.changeStar(list.get(position).id) {
                if (it)holder.itemView.item_liked.setImageResource(R.drawable.ic_star_yellow)
                else holder.itemView.item_liked.setImageResource(R.drawable.ic_star__white)
            }
        }
    }
    @Suppress("DEPRECATION")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        this.recyclerView.setOnScrollListener(mScrollListener)
    }
    class MovieHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }
}