package com.josiassena.restmocksample

import com.josiassena.restmocksample.api.GitHubApi
import com.josiassena.restmocksample.data.Profile
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author Josias Sena
 * @see Profile
 */
class ProfileInformationProvider(private val api: GitHubApi) {

    /**
     * Get the profile information for a username provided
     *
     * @param userName the username to get profile information for
     * @return a [Single] which is notified of the returned profile, or when an error occurs.
     */
    fun getProfileInformationForUsername(userName: String): Single<Profile> {
        return api.getUserProfile(userName)
                .subscribeOn(Schedulers.io())
                .timeout(15, TimeUnit.SECONDS) // Time out after 15 seconds of no response
                .observeOn(AndroidSchedulers.mainThread())
    }
}