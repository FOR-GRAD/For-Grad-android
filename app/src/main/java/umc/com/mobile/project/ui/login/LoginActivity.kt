package umc.com.mobile.project.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import umc.com.mobile.project.MainActivity
import umc.com.mobile.project.databinding.ActivityLoginBinding
import umc.com.mobile.project.ui.login.viewmodel.LoginViewModel
import kotlin.coroutines.coroutineContext

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
			viewModel.login()

			val intent = Intent(this@LoginActivity, MainActivity::class.java)
			startActivity(intent)
		}

		/*viewModel.loginStatus.observe(this, Observer {
			if (it == true) {
				val intent = Intent(this@LoginActivity, MainActivity::class.java)
				startActivity(intent)

				finish()
			}
		})*/
	}

}