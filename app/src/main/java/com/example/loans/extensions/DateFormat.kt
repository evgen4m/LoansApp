package com.example.loans.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(date: String) : String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale("ru"))
        val dateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return formatter.format(dateTime.parse(date)!!)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTimeFormat(date: String) : String {
        val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
        val dateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return formatter.format(dateTime.parse(date)!!)
    }

}