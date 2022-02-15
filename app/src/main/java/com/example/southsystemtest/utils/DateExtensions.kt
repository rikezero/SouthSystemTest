package com.example.southsystemtest.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.changeDateFormat(
    currentDateFormat: String,
    newDateFormat: String,
    locale: Locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR),
    isUTC: Boolean = false,
    shouldUseLocale: Boolean = false
): String {
    return try {
        val oldDf = SimpleDateFormat(currentDateFormat)
        val date = oldDf.parse(this)
        val newDf = if (shouldUseLocale) {
            SimpleDateFormat(
                newDateFormat,
                locale
            )
        } else {
            SimpleDateFormat(newDateFormat)
        }

        if (isUTC)
            newDf.timeZone = TimeZone.getTimeZone("gmt")

        newDf.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.dateFromString(
    dateFormat: String,
    locale: Locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR),
    isUTC: Boolean = false
): Date? {
    return try {
        val date = if (isUTC) changeDateFormat(dateFormat, dateFormat, isUTC = true) else this
        val sdf = SimpleDateFormat(dateFormat, locale)
        sdf.parse(date)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Long.toSimpleDate(
    dateFormat: String = "dd/MM/yyyy"
): String? {
    return try {
        val sdf = SimpleDateFormat(dateFormat)
        sdf.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
