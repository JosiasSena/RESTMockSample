package com.josiassena.restmocksample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.josiassena.restmocksample.api.GitHubApi
import com.josiassena.restmocksample.data.Profile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), AnkoLogger {

    // Build the GitHub api lazyly
    private val api: GitHubApi by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(GitHubApi::class.java)
    }

    // Build the ProfileInformationProvider api lazyly
    private val profileInformationProvider: ProfileInformationProvider by lazy {
        ProfileInformationProvider(api)
    }

    // Gson with pretty printing of Json enabled
    private val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnGetProfileInformation.setOnClickListener { getProfileInformation() }
    }

    private fun getProfileInformation() {
        val userName = etUsername.text.toString() // get the username from the edit text

        profileInformationProvider.getProfileInformationForUsername(userName)
                .subscribe({ profile: Profile? ->
                    // Do something with the profile information
                }, { throwable: Throwable? ->
                    // Do something on error
                })
    }
}
