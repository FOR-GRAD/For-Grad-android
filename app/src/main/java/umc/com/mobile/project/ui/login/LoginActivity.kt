package umc.com.mobile.project.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.com.mobile.project.MainActivity
import umc.com.mobile.project.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
	private lateinit var binding: ActivityLoginBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.btnLogin.setOnClickListener {
			val intent = Intent(this@LoginActivity, MainActivity::class.java)
			startActivity(intent)
		}
	}

}