package com.example.popularmovieskotlin.network

import com.example.popularmovieskotlin.BuildConfig
import com.example.popularmovieskotlin.model.ResultMovies
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.*

const val BASE_URL = "https://api.themoviedb.org/"
const val VERSION = "3"
const val PAGE = "page"
const val MOVIE = "movie"
const val VIDEOS = "videos"
const val REVIEWS = "reviews"
const val POPULAR = "popular"
const val FAVORITES = "favorites"
const val TOP_RATED = "top_rated"

interface MovieDbApiService {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(): ResultMovies

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val prop = Properties()


object RetrofitFactory {

    //creating a Network Interceptor to add api_key in all the request as authInterceptor
    private val interceptor = Interceptor { chain ->
        val url = chain.request().url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }


    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder().client(apiClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


}

object MoviesApi {
    val retrofitService: MovieDbApiService by lazy {
        RetrofitFactory.retrofit.create(MovieDbApiService::class.java)
    }
}
