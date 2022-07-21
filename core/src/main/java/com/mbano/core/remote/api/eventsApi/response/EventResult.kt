package com.mbano.core.remote.api.eventsApi.response

import com.google.gson.annotations.SerializedName

data class EventResult(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("subtitle")
    var subtitle: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("videoUrl")
    var videoUrl: String? = null

)