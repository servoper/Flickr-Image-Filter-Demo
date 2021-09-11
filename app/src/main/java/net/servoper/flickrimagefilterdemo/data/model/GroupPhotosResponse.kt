package net.servoper.flickrimagefilterdemo.data.model

import com.google.gson.annotations.SerializedName

data class GroupPhotosResponse(
    @SerializedName("stat")
    val status: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("photos")
    val data: PhotoList
)
