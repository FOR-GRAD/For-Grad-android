package umc.com.mobile.project.data.network

import android.util.Log
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.HttpCookie

object ApiClient {
	private const val BASE_URL = "http://52.79.167.79:8080/"
//	private const val BASE_URL = BuildConfig.BASE_URL
	private val cookieManager = CookieManager()

	private var builder = OkHttpClient().newBuilder()
	private var okHttpClient = builder
		.cookieJar(JavaNetCookieJar(CookieManager()))
		.build()

	internal val retrofit: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	internal inline fun <reified T> createService(): T {
		return retrofit.create(T::class.java)
	}

	internal fun addJSessionIdCookie(jSessionId: String) {
		val cookie = "JSESSIONID=$jSessionId"
		Log.d("cookie", jSessionId)
		cookieManager.cookieStore.add(null, HttpCookie.parse(cookie)[0])
	}
}