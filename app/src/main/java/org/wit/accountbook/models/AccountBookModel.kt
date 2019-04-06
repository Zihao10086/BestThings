package org.wit.accountbook.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//all the values of one record
@Parcelize  //it implements a parcelize capability
data class AccountBookModel(var id: Long = 0,
                            var fbId:String = "",//In order for firebase
                            var type: String = "",
                            var description:String = "",
                            var total : String = "",
                            var date:String = "",
                            var rating:Float = 0f,
                            var image:String = "",
                            var lat: Double = 0.0,
                            var lng: Double = 0.0,
                            var zoom: Float = 0f):Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,var lng:Double = 0.0,var zoom :Float = 0f):Parcelable