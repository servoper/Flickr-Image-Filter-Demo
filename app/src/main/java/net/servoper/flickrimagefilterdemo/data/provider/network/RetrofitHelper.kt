package net.servoper.flickrimagefilterdemo.data.provider.network

import net.servoper.flickrimagefilterdemo.BuildConfig
import net.servoper.flickrimagefilterdemo.data.GsonHelper
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitHelper {

    companion object {
        private val mOkHttpClient: OkHttpClient
        private lateinit var mInstance: HashMap<String, Retrofit>

        init {
            val okBuilder = OkHttpClient.Builder()

            okBuilder.addInterceptor { chain ->

                var request = chain.request()
                if (request.method == "POST") {
                    request = addApiParameters(request)
                }

                chain.proceed(request)
            }


            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()

                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okBuilder.addNetworkInterceptor(loggingInterceptor)
            }

            mOkHttpClient = okBuilder.build()

        }

        private fun addApiParameters(request: Request): Request {
            var request1 = request
            if (request1.body is FormBody) {
                val bodyBuilder = FormBody.Builder()
                var formBody = request1.body as FormBody?
                for (i in 0 until formBody!!.size) {
                    bodyBuilder.addEncoded(
                        formBody.encodedName(i),
                        formBody.encodedValue(i)
                    )
                }
                formBody = bodyBuilder
                    .addEncoded("api_key", BuildConfig.API_KEY)
                    .addEncoded("format", "json")
                    .addEncoded("nojsoncallback", "1")
                    .build()
                request1 = request1.newBuilder().post(formBody).build()
            }
            return request1
        }

        fun getInstance(baseUrl: String): Retrofit {
            if (!::mInstance.isInitialized) {
                mInstance = HashMap()
            }

            if (!mInstance.contains(baseUrl)) {
                mInstance[baseUrl] = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.get()))
                    .build()

            }

            return mInstance[baseUrl]!!
        }
    }
}