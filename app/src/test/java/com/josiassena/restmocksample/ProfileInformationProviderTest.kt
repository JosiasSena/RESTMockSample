package com.josiassena.restmocksample

import com.google.gson.Gson
import com.josiassena.restmocksample.api.GitHubApi
import com.josiassena.restmocksample.constants.MockResponses
import com.josiassena.restmocksample.data.Profile
import com.josiassena.restmocksample.rules.MockWebServerRule
import com.josiassena.restmocksample.rules.RxRule
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * @author Josias Sena
 */
class ProfileInformationProviderTest {

    private val tokenMatcher = RequestMatchers.pathContains("users/")

    @Rule
    @JvmField
    var mockWebServerRule = MockWebServerRule()

    companion object {

        @ClassRule
        @JvmField
        var rxRule = RxRule()

    }

    private lateinit var provider: ProfileInformationProvider

    @Before
    fun setUp() {
        // Build the GitHub api
        val retrofit = Retrofit.Builder()
                .baseUrl(RESTMockServer.getUrl()) // This is the test base url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val api = retrofit.create(GitHubApi::class.java)

        provider = ProfileInformationProvider(api)
    }

    @Test
    fun testGetProfileInformationForUserSuccess() {
        val mockResponse = MockResponse()
                .setBody(MockResponses.USER_PROFILE_MOCK_RESPONSE) // The response body
                .setResponseCode(200) // the response code to return

        RESTMockServer.whenGET(tokenMatcher).thenReturn(mockResponse)

        val testObserver = TestObserver.create<Profile>()
        provider.getProfileInformationForUsername("some_username").subscribe(testObserver)

        val profile = Gson().fromJson(MockResponses.USER_PROFILE_MOCK_RESPONSE, Profile::class.java)

        with(testObserver) {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue(profile)
            assertValueCount(1)
            assertComplete()
        }
    }

    @Test
    fun testGetProfileInformationTimeOut() {
        val mockResponse = MockResponse()
                .setBody(MockResponses.USER_PROFILE_MOCK_RESPONSE) // The response body
                .setResponseCode(200) // the response code to return

        RESTMockServer.whenGET(tokenMatcher).thenReturn(mockResponse)

        val testObserver = TestObserver.create<Profile>()
        provider.getProfileInformationForUsername("some_username").subscribe(testObserver)

        // Jump the mock network request to 15 seconds to cause a timeout to happen
        rxRule.testScheduler.advanceTimeBy(15, TimeUnit.SECONDS)

        with(testObserver) {
            awaitTerminalEvent()

            // The type of error thrown when a time out occurs
            assertError(TimeoutException::class.java)

            // Make sure no value is returned
            assertNoValues()
        }
    }

}