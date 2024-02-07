package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditActivityBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditActivityFragment : Fragment() {
    private var _binding: FragmentCareerEditActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private val sharedViewModel: CareerEditViewModel by activityViewModels()
    private val viewModel: CareerEditActivityViewModel by activityViewModels()
    private var startYear: String? = null
    private var startMonth: String? = null
    private var startDay: String? = null
    private var endYear: String? = null
    private var endMonth: String? = null
    private var endDay: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditActivityBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        mContext = requireContext()

        _binding!!.ivCareerActivityBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_activity_to_fragment_career_activity)
        }
        _binding!!.etCareerEditActivityStartYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, true, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditActivityEndYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, false, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditActivityFile.setOnClickListener {
            val bottomSheet = UploadBottomFragment(mContext, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.btnCareerEdit.setOnClickListener {
            viewModel.updateActivity()
            navigate(R.id.action_fragment_edit_activity_to_fragment_career)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        sharedViewModel.init()
        viewModel.fileAddedEvent.observe(viewLifecycleOwner, Observer { isFileAdded ->
            if (isFileAdded) {
                binding.etCareerEditActivityFile.setText("파일이 추가되었습니다")
                viewModel.fileAddedEvent.value = false // 이벤트를 처리했으므로 다시 false로 설정
            }
        })
        //버튼 활성화
        viewModel.isFilledAllOptions.observe(viewLifecycleOwner) { isEnabled ->
            binding?.btnCareerEdit?.isEnabled = isEnabled
            binding?.btnCareerEdit?.backgroundTintList =
                ContextCompat.getColorStateList(
                    requireContext(),
                    if (isEnabled) R.color.skyBlue else R.color.gray
                )
        }
        //교외활동 세부 내용 api 연결
        viewModel.getActivityDetail()
        //불러온 값 hint에 넣기
        viewModel.activityDetailInfo.observe(viewLifecycleOwner) { _activityInfo ->
            _activityInfo?.let {
                _binding?.etCareerEditActivity?.hint = it.result.title ?: ""
                val urls = _activityInfo.result.fileUrls.map { it.url }.joinToString("\n")
                _binding?.etCareerEditActivityFile?.hint = urls
                _binding?.etCareerEditActivityStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditActivityEndYear?.hint = it.result.endDate ?: ""
            }
        }
        //수정한 값 text에 띄우기
        sharedViewModel.selectedStartYear.observe(viewLifecycleOwner) { year ->
            startYear = year
            updateStartDateEditText()
        }
        sharedViewModel.selectedStartMonth.observe(viewLifecycleOwner) { month ->
            startMonth = month
            updateStartDateEditText()
        }
        sharedViewModel.selectedStartDay.observe(viewLifecycleOwner) { day ->
            startDay = day
            updateStartDateEditText()
        }
        sharedViewModel.selectedEndYear.observe(viewLifecycleOwner) { year ->
            endYear = year
            updateEndDateEditText()
        }
        sharedViewModel.selectedEndMonth.observe(viewLifecycleOwner) { month ->
            endMonth = month
            updateEndDateEditText()
        }
        sharedViewModel.selectedEndDay.observe(viewLifecycleOwner) { day ->
            endDay = day
            updateEndDateEditText()
        }
    }

    private fun buildFormattedDate(year: String?, month: String?, day: String?): String {
        return "$year$month$day"
    }

    private fun updateStartDateEditText() {
        val formattedDate = buildFormattedDate(startYear, startMonth, startDay)
        binding.etCareerEditActivityStartYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    private fun updateEndDateEditText() {
        val formattedDate = buildFormattedDate(endYear, endMonth, endDay)
        binding.etCareerEditActivityEndYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}