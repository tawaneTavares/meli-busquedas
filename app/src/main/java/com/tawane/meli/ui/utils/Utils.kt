package com.tawane.meli.ui.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency

fun Double.formatCurrency(currencyCode: String = "BRL"): String { // TODO: colocar moeda de acordo
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(currencyCode)
    return format.format(this)
}
