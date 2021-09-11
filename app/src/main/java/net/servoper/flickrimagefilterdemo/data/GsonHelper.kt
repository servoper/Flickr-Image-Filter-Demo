package net.servoper.flickrimagefilterdemo.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonHelper {
    companion object {
        private var mGson: Gson? = null

        fun get(): Gson {
            if (mGson == null) {
                mGson = GsonBuilder().create()
            }

            return mGson!!
        }
    }
}