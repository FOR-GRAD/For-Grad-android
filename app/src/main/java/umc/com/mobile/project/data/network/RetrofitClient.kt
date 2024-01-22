package umc.com.mobile.project.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
	companion object {
		private const val BASE_URL = "여러분의_베이스_URL_여기에"

		fun createRetrofit(): Retrofit {
			return Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
		}
	}
}