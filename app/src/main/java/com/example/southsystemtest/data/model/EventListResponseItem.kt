package com.example.southsystemtest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventListResponseItem(
    val date: Long?,
    val description: String?,
    val id: String?,
    val image: String?,
    val latitude: Number?,
    val longitude: Number?,
    val price: Number?,
    val title: String?
): Parcelable