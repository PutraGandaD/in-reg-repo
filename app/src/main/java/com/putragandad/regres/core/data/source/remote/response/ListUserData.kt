package com.putragandad.regres.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListUserData(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
)