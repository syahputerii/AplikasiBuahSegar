package com.syahna.myapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable