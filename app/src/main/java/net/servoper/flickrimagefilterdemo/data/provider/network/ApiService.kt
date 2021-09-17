package net.servoper.flickrimagefilterdemo.data.provider.network

import net.servoper.flickrimagefilterdemo.data.model.GroupPhotosResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

const val PHOTOS_PER_PAGE = 40

interface ApiService {
    @FormUrlEncoded
    @POST("rest")
    suspend fun getPhotosByGroup(
        @Field("page") page: Int = 1,
        @Field("group_id") groupId: String = "77216795@N00",
        @Field("method") method: String = "flickr.groups.pools.getPhotos",
        @Field("per_page") perPage: Int = PHOTOS_PER_PAGE
    ): Response<GroupPhotosResponse>
}