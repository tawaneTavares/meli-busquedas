package com.tawane.data.local.utils

import java.util.Calendar

fun getDateLongNow(): Long {
    val c1 = Calendar.getInstance()
    return c1.timeInMillis
}
