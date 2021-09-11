package net.servoper.flickrimagefilterdemo.repository

import net.servoper.flickrimagefilterdemo.data.model.GroupPhotosResponse
import net.servoper.flickrimagefilterdemo.data.provider.network.ApiProvider

class PhotosRepository {
    suspend fun getPhotosList(page: Int? = null): GroupPhotosResponse? {
        return page?.run {
            ApiProvider.instance.getPhotosByGroup(page = this).body()
        } ?: run {
            ApiProvider.instance.getPhotosByGroup().body()
        }
    }
}