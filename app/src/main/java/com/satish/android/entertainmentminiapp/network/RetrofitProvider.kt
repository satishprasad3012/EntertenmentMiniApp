package com.satish.android.entertainmentminiapp.network

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/


import android.os.Build
import com.satish.android.entertainmentminiapp.BuildConfig
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.app.EntertainmentApplication
import com.satish.android.entertainmentminiapp.utility.Constants.API_CONNECT_TIMEOUT_VALUE
import com.satish.android.entertainmentminiapp.utility.Constants.API_READ_TIMEOUT_VALUE
import com.satish.android.entertainmentminiapp.utility.defaultLocale
import com.satish.android.entertainmentminiapp.utility.deviceId
import com.satish.android.entertainmentminiapp.utility.log
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider private constructor() {
    val services: RetrofitServices

    private object Holder {
        val INSTANCE = RetrofitProvider()
    }

    init {
        services = defaultRetrofitClient.create(RetrofitServices::class.java)
    }

    companion object {
        val instance: RetrofitProvider by lazy { RetrofitProvider.Holder.INSTANCE }
        val defaultRetrofitClient: Retrofit = createRetrofitClient(defaultOkHttpClientBuilder)

        private fun createCertificatePinner(): CertificatePinner {
            val host = EntertainmentApplication.instance.getString(R.string.base_url)
                .replace("https://", "")
            return CertificatePinner.Builder().add(host, "sha256/satish") // add hash key here
                .build()
        }

        private fun createRetrofitClient(okttpClientBuilder: OkHttpClient.Builder) =
            Retrofit.Builder()
                .baseUrl(EntertainmentApplication.instance.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okttpClientBuilder.build())
                .build()

        private val defaultOkHttpClientBuilder: OkHttpClient.Builder
            get() {
                val okHttpClientBuilder = OkHttpClient.Builder()
                with(okHttpClientBuilder) {
                    connectTimeout(API_CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
                    readTimeout(API_READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
                    addInterceptor(headerInterceptor)
                }

                if (!BuildConfig.DEBUG) {
                    okHttpClientBuilder.certificatePinner(createCertificatePinner())
                }
                return okHttpClientBuilder
            }

        private val headerInterceptor: Interceptor
            get() = Interceptor { chain ->
                val jsonObject = JSONObject()
                try {
                    with(jsonObject) {
                        // below part is addition adding
                        put("deviceId", deviceId)
                        put("appVersion", BuildConfig.VERSION_NAME)
                        put("appVersionCode", BuildConfig.VERSION_CODE)
                        put("userLocale", defaultLocale)
                        put("osVersion", "Android_" + Build.VERSION.RELEASE)
                    }
                } catch (e: JSONException) {
                    e.log()
                }

                var request = chain.request()
                val builder = request.headers().newBuilder()
                    .add("Content-Type", "application/json")
                request = request.newBuilder().headers(builder.build()).build()
                chain.proceed(request)
            }
    }
}
