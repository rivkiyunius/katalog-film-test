package com.rivki.katalogfilm.modules.network

import com.rivki.katalogfilm.BuildConfig
import com.rivki.katalogfilm.api.ApiService
import com.rivki.katalogfilm.di.BaseModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModules: BaseModule {
    override val modules: List<Module>
        get() = listOf(
            retrofitModules,
            retrofitServiceModule
        )

    private val retrofitServiceModule = module {
        single { get<Retrofit>().create(ApiService::class.java) }
    }

    private val retrofitModules = module {
        single { provideOkHttpClient() }
        single { provideRetrofit(get()) }
    }
    private fun provideOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()
        with(httpClient){
            addInterceptor { chain ->
                val original = chain.request()
                val originHttpUrl = original.url
                val url = originHttpUrl
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            addInterceptor(logging)
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            readTimeout(30L, TimeUnit.SECONDS)
        }
        return httpClient.build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}