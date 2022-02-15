package com.example.southsystemtest.data.model

import retrofit2.Response
import retrofit2.Retrofit

class RetrofitException internal constructor(
    message: String?,
    exception: Throwable?,
    private val type: Type
) : Throwable(message, exception) {

    enum class Type {
        UNKNOWN, HTTP, NETWORK
    }

    var retrofit: Retrofit? = null
    var response: Response<*>? = null

    companion object {
        internal fun create(
            message: String?,
            exception: Throwable?,
            type: Type,
            retrofit: Retrofit? = null,
            response: Response<*>? = null
        ): RetrofitException {
            return RetrofitException(message, exception, type).apply {
                this.retrofit = retrofit
                this.response = response
            }
        }
    }
}
