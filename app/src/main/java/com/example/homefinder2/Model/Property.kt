package com.example.homefinder2.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Property(
    @SerializedName("id") val imageResourceId: String = "",
    @SerializedName("cost") val price: String = "",
    @SerializedName("script") val description: String = "",
    @SerializedName("address") val address: String = ""
) : Parcelable
