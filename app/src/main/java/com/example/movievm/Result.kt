package com.example.movievm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result{
    @SerializedName("results")
    @Expose
    var list:List<Movie>? = null
}