package com.example.niraj.searchengineapp.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTimeDateString(): String {
    val dateTime = Date(this * 1000)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return format.format(dateTime)
}
