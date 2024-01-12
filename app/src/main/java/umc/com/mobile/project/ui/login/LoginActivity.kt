package umc.com.mobile.project.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import umc.com.mobile.project.MainActivity
import umc.com.mobile.project.databinding.ActivityLoginBinding
import umc.com.mobile.project.ui.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
	private lateinit var binding: ActivityLoginBinding
	private val viewModel: LoginViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.vm = viewModel
		binding.lifecycleOwner = this

		binding.btnLogin.setOnClickListener {
			val intent = Intent(this@LoginActivity, MainActivity::class.java)
			startActivity(intent)
		}
	}

}