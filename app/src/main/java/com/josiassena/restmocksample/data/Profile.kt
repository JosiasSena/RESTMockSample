package com.josiassena.restmocksample.data

import com.google.gson.annotations.SerializedName

/**
 * @author Josias Sena
 */
data class Profile(@SerializedName("login") var login: String = "",
                   @SerializedName("id") var id: Int = 0,
                   @SerializedName("avatar_url") var avatarUrl: String = "",
                   @SerializedName("gravatar_id") var gravatarId: String = "",
                   @SerializedName("url") var url: String = "",
                   @SerializedName("html_url") var htmlUrl: String = "",
                   @SerializedName("followers_url") var followersUrl: String = "",
                   @SerializedName("following_url") var followingUrl: String = "",
                   @SerializedName("gists_url") var gistsUrl: String = "",
                   @SerializedName("starred_url") var starredUrl: String = "",
                   @SerializedName("subscriptio,ns_url") var subscriptionsUrl: String = "",
                   @SerializedName("organizations_url") var organizationsUrl: String = "",
                   @SerializedName("repos_url") var reposUrl: String = "",
                   @SerializedName("events_url") var eventsUrl: String = "",
                   @SerializedName("received_events_url") var receivedEventsUrl: String = "",
                   @SerializedName("type") var type: String = "",
                   @SerializedName("site_admin") var siteAdmin: Boolean = false,
                   @SerializedName("name") var name: String = "",
                   @SerializedName("company") var company: String = "",
                   @SerializedName("blog") var blog: String = "",
                   @SerializedName("location") var location: String = "",
                   @SerializedName("email") var email: String = "",
                   @SerializedName("hireable") var hireable: Boolean = false,
                   @SerializedName("bio") var bio: String = "",
                   @SerializedName("public_repos") var publicRepos: Int = 0,
                   @SerializedName("public_gists") var publicGists: Int = 0,
                   @SerializedName("followers") var followers: Int = 0,
                   @SerializedName("following") var following: Int = 0,
                   @SerializedName("created_at") var createdAt: String = "",
                   @SerializedName("updated_at") var updatedAt: String = "")