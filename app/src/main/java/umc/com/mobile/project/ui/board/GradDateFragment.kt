package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GradDateFragment : Fragment() {
	private var _binding: FragmentGradDateBinding? = null
	private lateinit var mContext: Context
	private var bottomSheetBinding: FragmentGradDateBottomBinding? = null
	private val viewModel: GradDateViewModel by activityViewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradDateBinding.inflate(inflater, container, false)
		mContext = requireContext()
		bottomSheetBinding =
			FragmentGradDateBottomBinding.inflate(layoutInflater)  // bottomSheetBinding 초기화

		with(binding) {
			tvGradDateDate.setOnClickListener {
				val bottomSheet = GradDateBottomFragment(mContext)
				bottomSheet.setStyle(
					DialogFragment.STYLE_NORMAL,
					umc.com.mobile.project.R.style.RoundCornerBottomSheetDialogTheme
				)
				bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
			}
		}

		//실패 코드-값을 눌러도 text 안 바뀜
		viewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
			binding.tvGradDateDate.text = selectedDate.toString()
		}

		navigateBack() // 뒤로 가기 버튼 클릭 시
		initTodayDate() // 오늘 날짜 설정
		saveCheeringMemo()
		viewModel.getDateInfo()

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun navigateBack() {
		binding.btnBack.setOnClickListener {
			navigate(R.id.action_fragment_date_to_fragment_home)
		}
	}

	private fun initTodayDate() {
		val currentTime = LocalDateTime.now()
		val pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

		binding.tvGradDateToday.text = currentTime.format(pattern)
	}

	private fun saveCheeringMemo() {
		binding.btnSave.setOnClickListener {
			viewModel.updateDateInfo(viewModel.selectedDateRequest.value.toString())

			Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_LONG).show()
		}
	}
}