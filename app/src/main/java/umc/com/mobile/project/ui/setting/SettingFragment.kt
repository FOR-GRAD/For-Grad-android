package umc.com.mobile.project.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.setting.viewmodel.SettingViewModel

class SettingFragment : Fragment() {
	private var _binding: FragmentSettingBinding? = null
	private val viewModel: SettingViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingBinding.inflate(inflater, container, false)

		navigateFragment()

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun navigateFragment() {
		binding.tvAppSettingNotification.setOnClickListener {
			navigate(R.id.action_fragment_setting_to_fragment_setting_notification)
		}
	}
}