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
import umc.com.mobile.project.databinding.FragmentCareerContestBinding
import umc.com.mobile.project.ui.career.adapter.ContestRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.CareerEditContestViewModel
import umc.com.mobile.project.ui.career.viewmodel.ContestViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ContestFragment : Fragment() {
    private var _binding: FragmentCareerContestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContestViewModel by viewModels()
    private val sharedViewModel: CareerEditContestViewModel by activityViewModels()
    val adapter = ContestRVAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerContestBinding.inflate(inflater, container, false)

        //돋보기 눌렀을 때 검색
        _binding!!.ivCareerContestSearch.setOnClickListener {
            performSearch()
        }
        //엔터 키로 검색
        _binding!!.etCareerContestSearchBar.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true  // 엔터 키를 눌렀을 때 이벤트 소비
            } else {
                false  // 그 외의 경우 이벤트 소비하지 않음
            }
        }

        _binding!!.ivCareerContestBack.setOnClickListener {
            navigate(R.id.action_fragment_contest_to_fragment_career)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //공모전 목록 api 연결
        viewModel.getContestInfo()
        // adapter 초기화
        binding.rvCareerContestList.adapter = adapter
        binding.rvCareerContestList.layoutManager = LinearLayoutManager(requireContext())

        //공모전 세부내용 api 연결
        viewModel.contestInfo.observe(viewLifecycleOwner, Observer { contestInfo ->
            adapter.updateItems(contestInfo?.result!!.activityWithAccumulatedHours)

            adapter.setOnItemClickListener(object : ContestRVAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    sharedViewModel.studentId.value =
                        contestInfo?.result!!.activityWithAccumulatedHours[position].id
                    navigate(R.id.action_fragment_contest_to_fragment_career_edit_contest)
                }
            })
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun performSearch() {
        val searchText = _binding!!.etCareerContestSearchBar.text.toString()

        if (searchText.isNotEmpty()) {
            viewModel.searchContestInfo(searchText)
            viewModel.searchInfo.observe(viewLifecycleOwner, Observer { searchInfo ->
                adapter.updateItems(searchInfo?.result!!.activityWithAccumulatedHours)
                adapter.setOnItemClickListener(object : ContestRVAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        sharedViewModel.studentId.value =
                            searchInfo?.result!!.activityWithAccumulatedHours[position].id
                        navigate(R.id.action_fragment_contest_to_fragment_career_edit_contest)
                    }
                })
                adapter.notifyDataSetChanged()
                _binding!!.etCareerContestSearchBar.text?.clear()
            })
        }
    }
}