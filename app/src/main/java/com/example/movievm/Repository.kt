package com.example.movievm

import android.content.Context
import com.example.movievm.GSON


open class Repository(context: Context){
    val apiRepo = ApiRepository.getInstance()
    val roomRepo = RoomRepository.getInstance(context)
}