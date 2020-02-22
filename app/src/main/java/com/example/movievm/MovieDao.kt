package com.example.movievm

import androidx.room.*
import kotlinx.coroutines.Deferred

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getElementById(id:Int): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieData: Movie)

    @Update
    fun changeStat(movieData: Movie)
}