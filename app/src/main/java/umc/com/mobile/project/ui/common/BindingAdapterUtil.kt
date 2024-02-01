package umc.com.mobile.project.ui.common

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapterUtil {
	@JvmStatic
	@BindingAdapter("app:visibleIf")
	fun setVisible(view: View, isVisible: Boolean) {
		view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
	}
}
