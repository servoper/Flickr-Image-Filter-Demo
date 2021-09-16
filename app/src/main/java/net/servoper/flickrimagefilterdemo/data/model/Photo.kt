package net.servoper.flickrimagefilterdemo.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner")
    val ownerId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ownername")
    val ownerName: String,
    @SerializedName("dateadded")
    val dateAdded: String,
    @SerializedName("farm")
    val farmId: String,
    @SerializedName("server")
    val serverId: String,
    @SerializedName("secret")
    val secret: String
)
