package com.example.popularmovieskotlin.network

import com.example.popularmovieskotlin.model.ResultMovies
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/"
private const val VERSION = "3"
private const val PAGE = "page"
private const val MOVIE = "movie"
private const val VIDEOS = "videos"
private const val REVIEWS = "reviews"
private const val POPULAR = "popular"
private const val FAVORITES = "favorites"
private const val TOP_RATED = "top_rated"

private const val PARAM_API_KEY = "api_key"


interface MovieDbApiService {

    @GET("3/movie/popular?api_key=xxxxxxx")
    suspend fun getPopularMovies(): Response<ResultMovies>

}


object RetrofitFactory {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun makeRetrofitService(): MovieDbApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(MovieDbApiService::class.java)
    }
}
