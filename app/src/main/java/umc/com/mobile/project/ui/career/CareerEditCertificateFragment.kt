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
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditCertificateBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditCertificateViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditCertificateFragment : Fragment() {
    private var _binding: FragmentCareerEditCertificateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private val sharedViewModel: CareerEditViewModel by activityViewModels()
    private val viewModel: CareerEditCertificateViewModel by activityViewModels()
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
        _binding = FragmentCareerEditCertificateBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        mContext = requireContext()
        _binding!!.ivCareerCertificateBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_certificate_to_fragment_career_certificate)
        }
        _binding!!.etCareerEditCertificateStartYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, true, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        _binding!!.etCareerEditCertificateEndYear.setOnClickListener {
            val bottomSheet = PeriodBottomFragment(mContext, false, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }

        _binding!!.tvCareerCertificateDelete.setOnClickListener {
            viewModel.deleteCertificate()
            navigate(R.id.action_fragment_edit_certificate_to_fragment_career)
        }

        _binding!!.btnCareerEdit.setOnClickListener {
            viewModel.updateCertificate()
            navigate(R.id.action_fragment_edit_certificate_to_fragment_career)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        sharedViewModel.init()
        //버튼 활성화
        viewModel.isFilledAllOptions.observe(viewLifecycleOwner) { isEnabled ->
            binding?.btnCareerEdit?.isEnabled = isEnabled
            binding?.btnCareerEdit?.backgroundTintList =
                ContextCompat.getColorStateList(
                    requireContext(),
                    if (isEnabled) R.color.skyBlue else R.color.gray
                )
        }
        //자격증 세부 내용 api 연결
        viewModel.getCertificateDetail()
        //불러온 값 hint에 넣어줌
        viewModel.certificateDetailInfo.observe(viewLifecycleOwner) { _certificateInfo ->
            _certificateInfo?.let {
                _binding?.etCareerEditCertificate?.hint = it.result.title ?: ""
                _binding?.etCareerEditCertificateType?.hint =
                    it.result.certificationType?.toString() ?: ""
                _binding?.etCareerEditCertificateStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditCertificateEndYear?.hint = it.result.endDate ?: ""
            }
        }
        //자격증 타입 선택 bottom frag 연결
        _binding!!.etCareerEditCertificateType.setOnClickListener {
            val bottomSheet = CertificateTypeBottomFragment(mContext, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        //수정한 값 text에 띄우기
        viewModel.type.observe(viewLifecycleOwner) { type ->
            binding.etCareerEditCertificateType.text =
                Editable.Factory.getInstance().newEditable(type)
        }
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
        binding.etCareerEditCertificateStartYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    private fun updateEndDateEditText() {
        val formattedDate = buildFormattedDate(endYear, endMonth, endDay)
        binding.etCareerEditCertificateEndYear.text =
            Editable.Factory.getInstance().newEditable(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}