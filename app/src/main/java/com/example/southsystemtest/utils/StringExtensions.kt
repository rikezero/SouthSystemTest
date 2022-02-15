package com.example.southsystemtest.utils

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

fun String.formatToHTTPS(): String {
    return this.replace("http://","https://")
}

fun Float.formatToCurrency(locale: Locale): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    return currencyFormatter.format(this.toDouble())
}

fun Float.formatToCurrency(locale: Locale, nearestRounding: Boolean): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale).apply {
        roundingMode = if (nearestRounding) RoundingMode.HALF_UP else RoundingMode.FLOOR
    }
    return currencyFormatter.format(this.toBigDecimal())
}

fun Double.formatToCurrency(locale: Locale): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    return currencyFormatter.format(this)
}

fun Float.formatCurrencyBRL(
    showCurrency: Boolean = true,
    nearestRounding: Boolean = false,
    valueWithDots: Boolean = false
): String {
    val locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR)
    val value = this.formatToCurrency(locale, nearestRounding)
    return if (valueWithDots) {
        if (showCurrency) value.replace(",", ".")
        else value.replace("R$", "").replace(",", ".").trimStart()
    } else {
        if (showCurrency) value else value.replace("R$", "")
    }
}

/**
 * @return the value with the BRL currency
 */
fun Float.formatCurrencyBRL(trimRs: Boolean = false): String {
    val locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR)
    return if (trimRs) this.formatToCurrency(locale).replace("R$", "") else this.formatToCurrency(
        locale
    )
}

fun Long.formatCurrencyBRL(trimRs: Boolean = false): String {
    val locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR)
    val string = (this.toDouble() / 100.0).formatToCurrency(locale)
    return if (trimRs) string.replace("R$", "") else string
}

fun Number.formatToAccurateCurrency(): String? =
    NumberFormat.getCurrencyInstance(
        Locale(
            Constants.LANGUAGE_PT,
            Constants.COUNTRY_BR
        )
    ).apply {
        roundingMode = RoundingMode.HALF_UP
    }.format(this)