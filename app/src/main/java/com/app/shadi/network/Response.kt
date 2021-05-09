package com.app.shadi.network

data class Response<out T>(val status: ResponseStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Response<T> = Response(status = ResponseStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Response<T> = Response(status = ResponseStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Response<T> = Response(status = ResponseStatus.LOADING, data = data, message = null)
    }
}