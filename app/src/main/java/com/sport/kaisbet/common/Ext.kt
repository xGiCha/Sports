package com.sport.kaisbet.common

import java.text.SimpleDateFormat
import java.util.*

fun String.timeStampToTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.toLong()
    val simpleDate = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    return simpleDate.format(calendar.time)
}

fun String.splitEventName(index: Int): String {
    val str = this.split("-")
    return when(index) {
            0, 1 -> str[index]
            else ->{ ""}
    }
}