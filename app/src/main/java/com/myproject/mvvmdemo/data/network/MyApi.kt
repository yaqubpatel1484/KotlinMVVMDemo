package com.myproject.mvvmdemo.data.network

import com.myproject.mvvmdemo.data.network.responses.AuthResponse
import com.myproject.mvvmdemo.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>   // here we are not returning 'Response' instead of 'Call'


    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    /*
    suspending function is a function that can be pause and resume at a later time
    so this type of function can running long running operations and wait for it to complete without blocking
     suspending functions are the center of everything in Coroutines .
     */


    @GET("quotes")
    suspend fun getQuotes() : Response<QuotesResponse>


    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            val logger: HttpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


            val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
            okHttp.addInterceptor(logger)
            okHttp.addInterceptor(networkConnectionInterceptor)

            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp.build())
                .build()
                .create(MyApi::class.java)
        }

    }


}