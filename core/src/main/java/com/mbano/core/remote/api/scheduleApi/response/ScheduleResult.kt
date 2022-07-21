package com.mbano.core.remote.api.scheduleApi.response


import com.google.gson.annotations.SerializedName


data class ScheduleResult(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("subtitle")
    var subtitle: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null

)