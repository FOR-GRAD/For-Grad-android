package umc.com.mobile.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import umc.com.mobile.project.databinding.ActivitySplash2Binding
import umc.com.mobile.project.databinding.ActivitySplashBinding

class Splash2Activity : AppCompatActivity() {
	private lateinit var binding: ActivitySplash2Binding
	private val delayMillis: Long = 2000 // 3초 딜레이

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySplash2Binding.inflate(layoutInflater)
		setContentView(binding.root)

		// 3초 딜레이 이후 LoginActivity로 이동
		Handler().postDelayed({
			startActivity(Intent(this, MainActivity::class.java))
//			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}, delayMillis)
	}


}