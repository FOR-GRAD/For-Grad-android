package umc.com.mobile.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import umc.com.mobile.project.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
	private lateinit var binding: ActivitySplashBinding
	private val delayMillis: Long = 2000 // 3초 딜레이

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySplashBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// 3초 딜레이 이후 LoginActivity로 이동
		Handler().postDelayed({
			startActivity(Intent(this, Splash2Activity::class.java))
//			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}, delayMillis)
	}


}