package com.example.movievm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_detail.view.*
import kotlinx.android.synthetic.main.movie_detail.view.movie_date
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieDetailFragment() :BaseFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail,container,false)
        val repository = Repository(context!!)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        val picasso = Picasso.get()
        if (arguments!=null) {
            if (arguments!!.containsKey("movie_details")) {
                val parce = this.arguments?.getParcelable<Movie>("movie_details")
                picasso.load("https://image.tmdb.org/t/p/original"+parce?.backdrop_path).into(view.backdrop_path)
                view.detail_rating.setText(parce?.vote_average.toString())
                view.movieName.setText(parce?.title)
                view.movie_date.setText(parce?.release_date)
                view.detail_rating.setText(parce?.vote_average.toString())
                Log.i("MovieDetailFragment",parce?.liked.toString())
                view.descriptionText.setText(parce?.overview)
                if(parce?.liked == true) {
                    view.liked_item.setImageResource(R.drawable.ic_star_yellow)
                }
            }
        }

        return view
    }
    override var fragment: Fragment
        get() = this
        set(value) {}

}