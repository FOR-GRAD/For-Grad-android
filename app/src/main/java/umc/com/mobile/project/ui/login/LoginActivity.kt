package umc.com.mobile.project.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
		}

		viewModel.loginStatus.observe(this, Observer { loginStatus ->
			if (!loginStatus) {
				val intent = Intent(this@LoginActivity, MainActivity::class.java)
				startActivity(intent)
				finish() // 로그인 성공시 로그인 액티비티 종료
			} else {
				// 로그인 실패시 처리할 로직 추가 가능
			}
		})

		binding.etPassword.setOnEditorActionListener { _, actionId, event ->
			if (actionId == EditorInfo.IME_ACTION_DONE ||
				(event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
			) {
				// 키패드 내리기
				val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
				imm.hideSoftInputFromWindow(binding.etPassword.windowToken, 0)

				// 로그인 버튼 클릭
				binding.btnLogin.performClick()
				return@setOnEditorActionListener true
			}
			false
		}
	}

}