package com.example.popularmovieskotlin.network

import com.example.popularmovieskotlin.BuildConfig
import com.example.popularmovieskotlin.model.ResultMovies
import com.example.popularmovieskotlin.model.ResultReviews
import com.example.popularmovieskotlin.model.ResultTrailers
import com.example.popularmovieskotlin.network.NetworkConstants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class NetworkConstants {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val VERSION = "3"
        const val PAGE = "page"
        const val MOVIE = "movie"
        const val VIDEOS = "videos"
        const val REVIEWS = "reviews"
        const val POPULAR = "popular"
        const val FAVORITES = "favorites"
        const val TOP_RATED = "top_rated"
    }
}

interface MovieDbApiService {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(): ResultMovies

    @GET("3/movie/{filter}")
    suspend fun getMovies(@Path("filter") filter: String): ResultMovies

    @GET("3/movie/{id}/videos")
    suspend fun getTrailers(@Path("id") id: String): ResultTrailers

    @GET("3/movie/{id}/reviews")
    suspend fun getReviews(@Path("id") id: String): ResultReviews

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

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

private val retrofit = Retrofit.Builder().client(apiClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object MoviesApi {
    val retrofitService: MovieDbApiService by lazy {
        retrofit.create(MovieDbApiService::class.java)
    }
}
