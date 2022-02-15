package com.example.southsystemtest.utils

import com.example.southsystemtest.data.model.ErrorResponse
import com.example.southsystemtest.data.model.RetrofitException
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

fun <T> Flow<T>.onCollect(
    onSuccess: (suspend (t: T) -> Unit)? = null,
    onError: ((e: Throwable) -> Unit)? = null,
    onLoading: ((loading: Boolean) -> Unit)? = null,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    coroutineScope.launch {
        CoroutineScope(Dispatchers.Main).launch {
            onLoading?.let { loading ->
                loading(true)
            }
        }
        try {
            collect { result ->
                onSuccess?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        it(result)
                        onLoading?.let { loading ->
                            loading(false)
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            onError?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    when(e){
                        is RetrofitException -> {
                            if (e.response?.raw()?.code() != 401) {
                                onError(e)
                            }
                        }
                        is HttpException -> {
                            val json = e.response()?.errorBody()?.string()
                            try {
                                val message = Gson().fromJson(json, ErrorResponse::class.java)
                                onError(RetrofitException.create(message.message, e, RetrofitException.Type.HTTP))
                            } catch (e: Exception) {
                                onError(e)
                            }
                        }
                        else -> {
                            onError(e)
                        }
                    }
                    onLoading?.let { loading ->
                        loading(false)
                    }
                }
            }
        }
    }
}