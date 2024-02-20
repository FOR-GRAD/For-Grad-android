package umc.com.mobile.project.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.com.mobile.project.databinding.FragmentSettingPolicyPrivacyBinding
import umc.com.mobile.project.ui.common.NavigationUtil.popBackStack

class SettingPolicyPrivacyFragment : Fragment() {
	private var _binding: FragmentSettingPolicyPrivacyBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingPolicyPrivacyBinding.inflate(inflater, container, false)

		binding.btnBack.setOnClickListener {
			popBackStack()
		}
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}