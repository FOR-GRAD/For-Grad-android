package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.PlanSubjectListBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class PlanTimeFragment : Fragment() {
    private var _binding: PlanSubjectListBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PlanSubjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // trackId와 hakki 값을 arguments에서 받아옵니다.
        val trackId = arguments?.getString("trackId") ?: ""
        val hakki = arguments?.getString("hakki") ?: ""

        // 수정: getListTimeInfo 함수를 한 번 호출하며, trackId와 hakki 값을 전달합니다.
        viewModel.getListTimeInfo(hakki, trackId)

        Log.d("PlanTimeFragment", "Received Hakki: $hakki, TrackId: $trackId")

        // RecyclerView 설정
        val adapter = PlanRecyclerAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // listTimeInfo LiveData를 관찰하여 RecyclerView를 업데이트합니다.
        viewModel.listTimeInfo.observe(viewLifecycleOwner) { timeListResponse ->
            timeListResponse?.let {
                // Adapter에 데이터를 설정하고, 변경을 알립니다.
                adapter.timeList = it.result
                adapter.notifyDataSetChanged()
            }
        }

        // Semester 및 Track 선택 버튼에 대한 클릭 리스너 설정
        binding.planSubjectListSemester.setOnClickListener {
            navigate(R.id.action_planSettingFragment_to_planSemesterFragment)
        }

        binding.planSubjectListTrack.setOnClickListener {
            navigate(R.id.action_planSettingFragment_to_planTrackFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
