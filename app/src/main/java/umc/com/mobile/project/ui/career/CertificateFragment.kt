package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
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
    val adapter = CertificateRVAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerCertificateBinding.inflate(inflater, container, false)
        //adapter 초기화
        binding.rvCareerCertificateList.adapter = adapter
        binding.rvCareerCertificateList.layoutManager = LinearLayoutManager(requireContext())

        //자격증 세부 내용 api 연결
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
        //돋보기 눌렀을 때 검색
        _binding!!.ivCareerCertificateSearch.setOnClickListener {
            performSearch()
        }
        //엔터 키로 검색
        _binding!!.etCareerCertificateSearchBar.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                performSearch()
                true  // 엔터 키를 눌렀을 때 이벤트 소비
            } else {
                false  // 그 외의 경우 이벤트 소비하지 않음
            }
        }

        _binding!!.ivCareerCertificateBack.setOnClickListener {
            navigate(R.id.action_fragment_certificate_to_fragment_career)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //자격증 목록 api 연결
        viewModel.getCertificateInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun performSearch() {
        val searchText = _binding!!.etCareerCertificateSearchBar.text.toString()

        if (searchText.isNotEmpty()) {
            viewModel.searchCertificateInfo(searchText)
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
                _binding!!.etCareerCertificateSearchBar.text?.clear()
            })
        }
    }
}