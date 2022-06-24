package com.app.practical.network

import com.google.gson.annotations.SerializedName

data class AppResponse<out T>(

    @field:SerializedName("message")
    val message: String? = "",
    @field:SerializedName("http_status_code")
    val code: Int = 0,
    @field:SerializedName("success")
    var success: Boolean = false,
    @field:SerializedName("data")
    val data: T? = null,
    val throwable: Throwable?
)