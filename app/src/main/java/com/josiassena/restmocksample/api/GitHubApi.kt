package com.josiassena.restmocksample.api

import com.josiassena.restmocksample.data.Profile
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Josias Sena
 */
interface GitHubApi {

    @GET("users/{username}")
    fun getUserProfile(@Path("username") username: String): Single<Profile>

}