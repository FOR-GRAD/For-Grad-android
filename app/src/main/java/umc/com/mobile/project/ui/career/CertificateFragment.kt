package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerCertificateBinding
import umc.com.mobile.project.ui.career.adapter.CertificateRVAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.viewmodel.CertificateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CertificateFragment : Fragment() {
    private var _binding: FragmentCareerCertificateBinding? = null
    private val viewModel: CertificateViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerCertificateBinding.inflate(inflater, container, false)
        val certificates = arrayListOf(
            CertificateDto("2022-01-01", "자격증1", "필기", "1급"),
            CertificateDto("2022-02-15", "자격증2", "필기", "1급"),
            CertificateDto("2022-03-30", "자격증3", "실기", "1급"),
            CertificateDto("2022-02-15", "자격증4", "필기", "1급"),
            CertificateDto("2022-03-30", "자격증5", "실기", "1급")
        )
        val adapter = CertificateRVAdapter()
        binding.rvCareerCertificateList.adapter = adapter
        binding.rvCareerCertificateList.layoutManager = LinearLayoutManager(requireContext())

        _binding!!.ivCareerCertificateBack.setOnClickListener {
            navigate(R.id.action_fragment_certificate_to_fragment_career)
        }
        viewModel.certificates.observe(viewLifecycleOwner, { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}