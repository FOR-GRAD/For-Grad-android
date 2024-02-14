package umc.com.mobile.project.ui.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.MainActivity
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentSettingBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.login.LoginActivity
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

		binding.tvEtcLogout.setOnClickListener {
			var dialog = AlertDialog.Builder(requireContext())
			dialog.setTitle("로그아웃을 하시겠습니까?")
			dialog.setMessage("서비스를 이용해주셔서 감사합니다.")

			fun toast() {
				Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
//				MyApplication.prefs.edit.remove("email") // 여기서 Shared Preference 를 remove 한다!
//				MyApplication.prefs.edit.remove("password")
//				MyApplication.prefs.edit.commit() // SP 삭제되는 것을 확인
				val intent = Intent(requireContext(), LoginActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
				startActivity(intent)
			}
			var dialogLister = DialogInterface.OnClickListener { p0, p1 ->
				when (p1) {
					DialogInterface.BUTTON_POSITIVE -> toast()
				}
			}
			dialog.setPositiveButton("YES", dialogLister)
			dialog.setNegativeButton("NO", null)
			dialog.show()
		}
	}
}