package umc.com.mobile.project.ui.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.MainActivity
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.home.viewmodel.HomeViewModel
import umc.com.mobile.project.ui.login.LoginActivity
import umc.com.mobile.project.ui.setting.viewmodel.SettingViewModel

class SettingFragment : Fragment() {
	private var _binding: FragmentSettingBinding? = null
	private val viewModel: HomeViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingBinding.inflate(inflater, container, false)

		navigateFragment()
		viewModel.getUserInfo() // 홈 화면 정보 조회 api

		viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
			binding.tvAccountIdContent.text = it?.result?.id.toString()
			Log.d("setting", it?.result?.id.toString())

			binding.tvAccountMajorContent.text = it?.result?.department
		})

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

		binding.tvEtcLogout.setOnClickListener {
			var dialogBuilder =
				AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogTheme)

			val customView = layoutInflater.inflate(R.layout.custom_dialog_logout, null)
			dialogBuilder.setView(customView)
			val alertDialog = dialogBuilder.create()

			alertDialog.show()

			customView.findViewById<Button>(R.id.button_positive).setOnClickListener {
//				Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
				val intent = Intent(requireContext(), LoginActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
				intent.putExtra("logged_out", true)
				startActivity(intent)
				requireActivity().finish()
			}

			customView.findViewById<Button>(R.id.button_negative).setOnClickListener {
				alertDialog.dismiss()
			}

		}

	}
}