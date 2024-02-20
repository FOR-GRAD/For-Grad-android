package umc.com.mobile.project.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.databinding.FragmentSettingServiceTermsBinding
import umc.com.mobile.project.ui.common.NavigationUtil.popBackStack

class SettingServiceTermsFragment : Fragment() {
	private var _binding: FragmentSettingServiceTermsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingServiceTermsBinding.inflate(inflater, container, false)

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