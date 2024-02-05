package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerCertificateBinding
import umc.com.mobile.project.ui.career.adapter.CertificateRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.CareerEditCertificateViewModel
import umc.com.mobile.project.ui.career.viewmodel.CertificateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CertificateFragment : Fragment() {
    private var _binding: FragmentCareerCertificateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CertificateViewModel by viewModels()
    private val sharedViewModel: CareerEditCertificateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerCertificateBinding.inflate(inflater, container, false)
        // adapter 초기화
        val adapter = CertificateRVAdapter(emptyList())
        binding.rvCareerCertificateList.adapter = adapter
        binding.rvCareerCertificateList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCertificateInfo()
        viewModel.certificateInfo.observe(viewLifecycleOwner, Observer { certificateInfo ->
            adapter.updateItems(certificateInfo?.result!!.activityWithAccumulatedHours)
            adapter.setOnItemClickListener(object : CertificateRVAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    sharedViewModel.studentId.value =
                        certificateInfo?.result!!.activityWithAccumulatedHours[position].id
                    navigate(R.id.action_fragment_certificate_to_fragment_career_edit_certificate)
                }
            })
            adapter.notifyDataSetChanged()
        })
        _binding!!.ivCareerCertificateSearch.setOnClickListener {
            viewModel.searchCertificateInfo(_binding!!.etCareerCertificateSearchBar.text.toString())
            viewModel.searchInfo.observe(viewLifecycleOwner, Observer { searchInfo ->
                adapter.updateItems(searchInfo?.result!!.activityWithAccumulatedHours)
                adapter.setOnItemClickListener(object : CertificateRVAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        sharedViewModel.studentId.value =
                            searchInfo?.result!!.activityWithAccumulatedHours[position].id
                        navigate(R.id.action_fragment_certificate_to_fragment_career_edit_certificate)
                    }
                })
                adapter.notifyDataSetChanged()
            })
        }
        _binding!!.ivCareerCertificateBack.setOnClickListener {
            navigate(R.id.action_fragment_certificate_to_fragment_career)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}