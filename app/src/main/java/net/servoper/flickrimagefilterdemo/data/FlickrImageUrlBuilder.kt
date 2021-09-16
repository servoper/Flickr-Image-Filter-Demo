package net.servoper.flickrimagefilterdemo.data

import android.content.Context
import net.servoper.flickrimagefilterdemo.R

class FlickrImageUrlBuilder {
    companion object {
        fun getUrl(
            context: Context,
            farmId: String,
            serverId: String,
            photoId: String,
            secret: String
        ) = context.getString(R.string.flick_image_url, farmId, serverId, photoId, secret)
    }
}