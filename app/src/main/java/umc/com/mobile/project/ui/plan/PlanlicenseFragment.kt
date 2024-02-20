package umc.com.mobile.project.ui.plan

import android.content.res.ColorStateList
import umc.com.mobile.project.data.model.plan.SaveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentPlanlicenseBinding

class PlanlicenseFragment : Fragment() {
	private var _binding: FragmentPlanlicenseBinding? = null
	private val viewModel: PlanViewModel by activityViewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPlanlicenseBinding.inflate(inflater, container, false)

		viewModel.getLicenseInfo() // api 연결

		// UPlicenseResponse-자격증 정보 불러오기
		viewModel.licenseInfo.observe(viewLifecycleOwner) { licenseInfo ->
			licenseInfo?.result?.let { resultList ->
				// 첫 번째 Result 객체에 접근하여 데이터 매핑
				resultList.getOrNull(0)?.let { firstResult ->
					binding.planLicenseName.setText(firstResult.name)
					binding.planLicenseDate.setText(firstResult.date)
				}

				// 두 번째 Result 객체에 접근하여 데이터 매핑
				resultList.getOrNull(1)?.let { secondResult ->
					binding.planLicenseName2.setText(secondResult.name)
					binding.planLicenseDate2.setText(secondResult.date)

				}

				resultList.getOrNull(2)?.let { thirdResult ->
					binding.planLicenseName3.setText(thirdResult.name)
					binding.planLicenseDate3.setText(thirdResult.date)


				}

				resultList.getOrNull(3)?.let { fourthResult ->
					binding.planLicenseName4.setText(fourthResult.name)
					binding.planLicenseDate4.setText(fourthResult.date)
				}
			}
		}

		return binding.root
	}


	private fun submitData() {

		val name = binding.planLicenseName.text.toString()
		val date = binding.planLicenseDate.text.toString()

		// SaveInfo 객체 리스트 생성
		val saveInfoList = listOf(SaveInfo(name, date))

		// SavelicenseRequest 객체 생성

		// API 호출
		viewModel.saveLicense(saveInfoList)

	}


	private fun setupEditTextListener() {
		val textWatcher = object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
				// 필요 없음
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				// 입력이 변경될 때마다 호출됩니다.
				checkIfAnyInputIsFilled()
			}

			override fun afterTextChanged(s: Editable?) {
				// 필요 없음
			}
		}
		binding.planLicenseName.addTextChangedListener(textWatcher)
	}

	private fun checkIfAnyInputIsFilled() {
		val isAnyFieldFilled = binding.planLicenseName.text.trim().isNotEmpty()
		//버튼 활성화 업데이트
		binding.licenseButtonStore.isEnabled = isAnyFieldFilled

		// 버튼 색상도 업데이트
		val colorResId = if (isAnyFieldFilled) R.color.skyBlue else R.color.gray
		val color = ContextCompat.getColor(requireContext(), colorResId)
		binding.licenseButtonStore.backgroundTintList = ColorStateList.valueOf(color)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupEditTextListener()
		checkIfAnyInputIsFilled() // 초기 상태 확인
		binding.licenseButtonStore.setOnClickListener {
			submitData() // 사용자 입력을 기반으로 API 호출
		}
		binding.licenseDeleteButton.setOnClickListener {
			deleteLicense() // 삭제 API 호출
		}
	}

	private fun deleteLicense() {
		// 삭제할 자격증 ID
		val certificateId = 1 // 예시로 1을 사용하였습니다. 실제로는 삭제할 자격증의 ID를 전달해야 합니다.

		// API 호출
//        viewModel.deleteLicense(certificateId)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}