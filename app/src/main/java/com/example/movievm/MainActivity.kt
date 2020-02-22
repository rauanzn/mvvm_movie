package com.example.movievm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    companion object{
        val detail = MovieDetailFragment()
        val list = MovieList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.mainFragment,list).show(list).commit()
        detail.navigation = {
            val trans = supportFragmentManager.beginTransaction()
            trans.add(R.id.mainFragment,detail).addToBackStack(null)
            trans.commit()
            null
        }
        detail.navigationHide={
            val trans = supportFragmentManager.beginTransaction()
            trans.remove(detail)
            trans.commit()
            null
        }
        list.navigation = {
            val trans = supportFragmentManager.beginTransaction()
            trans.show(list)
            trans.commit()
            null
        }
        list.navigationHide={
            val trans = supportFragmentManager.beginTransaction()
            trans.hide(list)
            trans.commit()
            null
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.fragments.size == 2) {
            supportFragmentManager.beginTransaction().remove(detail).commit()
            supportFragmentManager.beginTransaction().show(list).commit()
        }
    }
}
