package net.servoper.flickrimagefilterdemo.data.provider.network

import net.servoper.flickrimagefilterdemo.BuildConfig

class ApiProvider {
    companion object {
        val instance: ApiService = RetrofitHelper.getInstance(BuildConfig.API_URL)
            .create(ApiService::class.java)
    }
}