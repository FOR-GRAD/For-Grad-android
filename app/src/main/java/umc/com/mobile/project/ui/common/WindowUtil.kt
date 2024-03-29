package umc.com.mobile.project.ui.common

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object WindowUtil {
	fun Activity.setStatusBarColor(color: Int) {
		window.statusBarColor = ContextCompat.getColor(this, color)
	}

	fun Fragment.setStatusBarColor(color: Int) {
		requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), color)
	}

	fun Activity.setNavigationBarColor(color: Int) {
		window.navigationBarColor = ContextCompat.getColor(this, color)
	}

	fun Fragment.setNavigationBarColor(color: Int) {
		requireActivity().window.navigationBarColor = ContextCompat.getColor(requireActivity(), color)
	}

	fun Fragment.hideKeyboard() {
		val inputManager =
			requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
		inputManager.hideSoftInputFromWindow(
			requireActivity().currentFocus!!.windowToken,
			InputMethodManager.HIDE_NOT_ALWAYS
		)
	}
}