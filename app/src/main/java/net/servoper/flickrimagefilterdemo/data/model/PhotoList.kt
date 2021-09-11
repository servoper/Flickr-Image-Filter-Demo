package net.servoper.flickrimagefilterdemo.data.model

import com.google.gson.annotations.SerializedName

data class PhotoList(
    @SerializedName("page")
    var page: Int,
    @SerializedName("pages")
    var pages: Int,
    @SerializedName("perpage")
    var itemsPerPage: Int,
    @SerializedName("total")
    var totalCount: Int,
    @SerializedName("photo")
    val photosList: ArrayList<Photo>
)
