package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerVolunteerBinding
import umc.com.mobile.project.ui.career.adapter.VolunteerRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.CareerEditVolunteerViewModel
import umc.com.mobile.project.ui.career.viewmodel.VolunteerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class VolunteerFragment : Fragment() {
    private var _binding: FragmentCareerVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VolunteerViewModel by viewModels()
    private val sharedViewModel: CareerEditVolunteerViewModel by activityViewModels()
    val adapter = VolunteerRVAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerVolunteerBinding.inflate(inflater, container, false)

        //adapter 초기화
        binding.rvCareerVolunteerList.adapter = adapter
        binding.rvCareerVolunteerList.layoutManager = LinearLayoutManager(requireContext())
        //봉사 활동 목록 api 연결
        viewModel.getVolunteerInfo()
        //봉사 활동 세부 내용 api 연결
        viewModel.volunteerInfo.observe(viewLifecycleOwner, Observer { volunteerInfo ->
            adapter.updateItems(volunteerInfo?.result!!.activityWithAccumulatedHours)

            adapter.setOnItemClickListener(object : VolunteerRVAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    sharedViewModel.studentId.value =
                        volunteerInfo?.result!!.activityWithAccumulatedHours[position].id
                    navigate(R.id.action_fragment_volunteer_to_fragment_career_edit_volunteer)
                }
            })
            adapter.notifyDataSetChanged()
        })

        //돋보기 눌렀을 때 검색
        _binding!!.ivCareerVolunteerSearch.setOnClickListener {
            performSearch()
        }
        //엔터 키로 검색
        _binding!!.etCareerVolunteerSearchBar.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true  // 엔터 키를 눌렀을 때 이벤트 소비
            } else {
                false  // 그 외의 경우 이벤트 소비하지 않음
            }
        }

        _binding!!.ivCareerVolunteerBack.setOnClickListener {
            navigate(R.id.action_fragment_volunteer_to_fragment_career)
        }
        return binding.root
    }

    private fun performSearch() {
        val searchText = _binding!!.etCareerVolunteerSearchBar.text.toString()

        if (searchText.isNotEmpty()) {
            viewModel.searchVolunteerInfo(searchText)
            viewModel.searchInfo.observe(viewLifecycleOwner, Observer { searchInfo ->
                adapter.updateItems(searchInfo?.result!!.activityWithAccumulatedHours)
                adapter.setOnItemClickListener(object : VolunteerRVAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        sharedViewModel.studentId.value =
                            searchInfo?.result!!.activityWithAccumulatedHours[position].id
                        navigate(R.id.action_fragment_volunteer_to_fragment_career_edit_volunteer)
                    }
                })
                adapter.notifyDataSetChanged()
                _binding!!.etCareerVolunteerSearchBar.text?.clear()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}