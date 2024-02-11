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
import umc.com.mobile.project.databinding.FragmentCareerActivityBinding
import umc.com.mobile.project.ui.career.adapter.ActivityRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.ActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ActivityFragment : Fragment() {
    private var _binding: FragmentCareerActivityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActivityViewModel by viewModels()
    private val sharedViewModel: CareerEditActivityViewModel by activityViewModels()
    val adapter = ActivityRVAdapter(emptyList())
//머지 테스트중
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerActivityBinding.inflate(inflater, container, false)
        //adapter 초기화
        binding.rvCareerActivityList.adapter = adapter
        binding.rvCareerActivityList.layoutManager = LinearLayoutManager(requireContext())
        //대외 활동 세부 내용 api 연결
        viewModel.activityInfo.observe(viewLifecycleOwner, Observer { activityInfo ->
            adapter.updateItems(activityInfo?.result!!.activityWithAccumulatedHours)
            adapter.setOnItemClickListener(object : ActivityRVAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    sharedViewModel.studentId.value =
                        activityInfo?.result!!.activityWithAccumulatedHours[position].id
                    navigate(R.id.action_fragment_activity_to_fragment_career_edit_activity)
                }
            })
            adapter.notifyDataSetChanged()
        })

        //돋보기 눌렀을 때 검색
        _binding!!.ivCareerActivitySearch.setOnClickListener {
            performSearch()
        }
        //엔터 키로 검색
        _binding!!.etCareerActivitySearchBar.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true  // 엔터 키를 눌렀을 때 이벤트 소비
            } else {
                false  // 그 외의 경우 이벤트 소비하지 않음
            }
        }

        _binding!!.ivCareerActivityBack.setOnClickListener {
            navigate(R.id.action_fragment_activity_to_fragment_career)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //대외 활동 api 연결
        viewModel.getActivityInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun performSearch() {
        val searchText = _binding!!.etCareerActivitySearchBar.text.toString()

        if (searchText.isNotEmpty()) {
            viewModel.searchActivityInfo(searchText)
            viewModel.searchInfo.observe(viewLifecycleOwner, Observer { searchInfo ->
                adapter.updateItems(searchInfo?.result!!.activityWithAccumulatedHours)
                adapter.setOnItemClickListener(object : ActivityRVAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        sharedViewModel.studentId.value =
                            searchInfo?.result!!.activityWithAccumulatedHours[position].id
                        navigate(R.id.action_fragment_activity_to_fragment_career_edit_activity)
                    }
                })
                adapter.notifyDataSetChanged()
                _binding!!.etCareerActivitySearchBar.text?.clear()
            })
        }
    }
}