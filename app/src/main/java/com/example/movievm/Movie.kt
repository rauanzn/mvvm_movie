package com.example.movievm

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id:Int,
    var poster_path:String?=null,
    var original_title:String,
    var title:String,
    var popularity:Double,
    var vote_average:Double,
    var release_date:String,
    var backdrop_path:String?=null,
    var overview:String,
    var liked:Boolean=false

) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(poster_path)
        dest?.writeString(original_title)
        dest?.writeString(title)
        dest?.writeString(release_date)
        dest?.writeString(backdrop_path)
        dest?.writeString(overview)
        dest?.writeDouble(popularity)
        dest?.writeDouble(vote_average)
        dest?.writeInt(id)
        dest?.writeBoolean(liked)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }


        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}