package com.projectfire.campusinventoryappliction.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val user_id: String = "",
    val user_name: String = "",
    val items: ArrayList<CartItem> = ArrayList(),
    val address: String = "",
    val bitsid: String = "",
    val mobile: String ="",
    val title: String = "",
    val image: String = "",
    val total_amount: String = "",
    val date: String = "",
    var order_id: String ="",
    var id: String = ""
) : Parcelable