package net.servoper.flickrimagefilterdemo.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val ownerId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ownername")
    val ownerName: String,
    @SerializedName("dateadded")
    val dateAdded: String,
    @SerializedName("farm")
    var farmId: String,
    @SerializedName("server")
    var serverId: String,
    @SerializedName("secret")
    var secret: String
)
