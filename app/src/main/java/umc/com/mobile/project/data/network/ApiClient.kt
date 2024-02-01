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
val cookieManager = CookieManager()

	private var builder = OkHttpClient().newBuilder()
	private var okHttpClient = builder
		.cookieJar(JavaNetCookieJar(CookieManager()))
		.addInterceptor { chain ->
			val original = chain.request()
			val request = original.newBuilder()
				.header("Content-Type", "multipart/form-data")
				.method(original.method, original.body)
				.build()
			chain.proceed(request)
		}
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
}