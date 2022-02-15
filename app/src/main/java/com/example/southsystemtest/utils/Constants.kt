package com.example.southsystemtest.utils

import com.example.southsystemtest.BuildConfig

object Constants {
    const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    const val LANGUAGE_PT = "pt"
    const val COUNTRY_BR = "BR"
    const val DATE_TIME_BR_FORMAT = "dd/MM/yyyy - hh:MM"
    const val SHARE_HELPER_DATE_FORMAT = "yyyyMMdd_HHmmss"
    const val DATE_BR_FORMAT = "dd/MM/yyyy"
    const val AUTHORITY = Constants.PACKAGE_NAME + ".fileprovider"
}