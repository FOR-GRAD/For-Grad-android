package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditCertificateBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditCertificateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditCertificateFragment : Fragment() {
    private var _binding: FragmentCareerEditCertificateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CareerEditCertificateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditCertificateBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        _binding!!.ivCareerCertificateBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_certificate_to_fragment_career_certificate)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //API 연결
        viewModel.getCertificateDetail()
        // 불러온 값 hint에 넣어줌
        viewModel.certificateDetailInfo.observe(viewLifecycleOwner) { _certificateInfo ->
            _certificateInfo?.let {
                _binding?.etCareerEditCertificate?.hint = it.result.title ?: ""
                _binding?.etCareerEditCertificateType?.hint =
                    it.result.certificationType?.toString() ?: ""
                _binding?.etCareerEditCertificateStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditCertificateEndYear?.hint = it.result.endDate ?: ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}