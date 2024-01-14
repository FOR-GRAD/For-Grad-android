package umc.com.mobile.project.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.databinding.FragmentSettingNotificationBinding
import umc.com.mobile.project.ui.common.NavigationUtil.popBackStack
import umc.com.mobile.project.ui.setting.viewmodel.SettingViewModel

class SettingNotificationFragment : Fragment() {
	private var _binding: FragmentSettingNotificationBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingNotificationBinding.inflate(inflater, container, false)

		binding.btnBack.setOnClickListener {
			popBackStack()
		}

		return binding.root
	}
}