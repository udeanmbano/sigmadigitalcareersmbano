package com.mbano.core.testing.mock.data

import com.mbano.core.testing.mock.data.MockWebServices

interface MockResponse {

    fun success(): MockWebServices

    fun networkFailure(): MockWebServices

    fun apiFailure(): MockWebServices

    fun httpFailure(): MockWebServices

    fun unknownFailure(): MockWebServices
}