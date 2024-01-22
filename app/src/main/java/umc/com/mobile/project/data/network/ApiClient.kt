package umc.com.mobile.project.data.network

import retrofit2.Retrofit

object ApiClient {
	private val retrofit: Retrofit by lazy {
		RetrofitClient.createRetrofit()
	}

	private inline fun <reified T> createService(): T {
		return retrofit.create(T::class.java)
	}
}