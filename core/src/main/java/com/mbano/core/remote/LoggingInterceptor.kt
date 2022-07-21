package com.mbano.core.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.IOException

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.i(request.toLog())

        val response = chain.proceed(request)
        Timber.i(response.toLog())

        return response
    }
}

//TODO: Relocate this extension function somewhere more appropriate
fun Request.toLog(): String {
    val logBuilder = StringBuilder()
    logBuilder.append("-->\n")
    logBuilder.append("\n<<<<<<<<<<<<<<<<<<<<NETWORK REQUEST>>>>>>>>>>>>>>>>>>>>\n")
    logBuilder.append("Method: ${this.method}\n")
    logBuilder.append("URL: ${this.url}\n")
    logBuilder.append("Headers: ${this.headers}\n")
    logBuilder.append("Body: ${this.body?.toLog()}\n")
    logBuilder.append("<<<<<<<<<<<<<<<<<<<<END NETWORK>>>>>>>>>>>>>>>>>>>>\n\n")
    logBuilder.append("<--")
    return logBuilder.toString()
}

fun RequestBody.toLog(): String {
    return try {
        val buffer = Buffer()
        this.writeTo(buffer)
        buffer.readUtf8()
    } catch (ioe: IOException) {
        "Could not read body"
    }
}

fun Response.toLog(): String {
    val logBuilder = StringBuilder()
    logBuilder.append("-->\n")
    logBuilder.append("\n<<<<<<<<<<<<<<<<<<<<NETWORK RESPONSE>>>>>>>>>>>>>>>>>>>>\n")
    logBuilder.append("Status Code: ${this.code}\n")
    logBuilder.append("Message: ${this.message}\n")
    logBuilder.append("URL: ${this.request.url}\n")
    logBuilder.append("Headers: ${this.headers}\n")

    val body = this.peekBody(Long.MAX_VALUE)
    logBuilder.append("Body: ${body.string()}\n")
    logBuilder.append("<<<<<<<<<<<<<<<<<<<<END NETWORK>>>>>>>>>>>>>>>>>>>>\n\n")
    logBuilder.append("<--")

    return logBuilder.toString()
}