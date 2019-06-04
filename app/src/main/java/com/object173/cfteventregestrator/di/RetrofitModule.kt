package com.object173.cfteventregestrator.di

import android.content.Context
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.io.IOException
import com.google.gson.GsonBuilder
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.device.NoConnectivityException
import com.object173.cfteventregestrator.feature.base.device.isOnline
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    @Inject
    fun getRetrofit(context : Context) : Retrofit {
        return Retrofit.Builder()
            .client(createClient(context))
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(context.getString(R.string.base_url))
            .build()

    }

    private fun getGson() : Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    }

    private fun createClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor (object : Interceptor {
            private val authAttr = context.getString(R.string.auth_attr)
            private val authToken = context.getString(R.string.auth_token)

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                if (!isOnline(context)) {
                    throw NoConnectivityException()
                }
                val url = chain.request().url().newBuilder()
                    .addQueryParameter(authAttr, authToken)
                    .build()
                val newRequest = chain.request().newBuilder()
                    .url(url)
                    .build()
                return chain.proceed(newRequest)
            }
        }).build()
    }
}