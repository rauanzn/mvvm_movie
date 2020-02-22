package com.example.movievm

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

abstract class BaseFragment :Fragment(){
    abstract var fragment:Fragment
    var navigation: (() -> Unit?)? = null
    var navigationHide: (() -> Unit)? = null
    fun show(){
        navigation?.invoke()
    }
    fun hide(){
        navigationHide?.invoke()
    }

}