package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.PlanSubjectListBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate


class PlanTimeFragment : Fragment() {
    private var _binding: PlanSubjectListBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PlanSubjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlanRecyclerAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 학기(hakki)와 트랙(trackId) 값이 모두 준비되었을 때 데이터 로드
        viewModel.hakki.observe(viewLifecycleOwner) { hakki ->
            Log.d("PlanTimeFragment", "Observed hakki: $hakki")
            val trackId = viewModel.track.value
            if (hakki.isNotEmpty() && trackId != null && trackId.isNotEmpty()) {
                viewModel.getListTimeInfo(hakki, trackId)
            }
        }

        viewModel.track.observe(viewLifecycleOwner) { trackId ->
            Log.d("PlanTimeFragment", "Observed trackId: $trackId")
            val hakki = viewModel.hakki.value
            if (trackId.isNotEmpty() && hakki != null && hakki.isNotEmpty()) {
                viewModel.getListTimeInfo(hakki, trackId)
            }
        }

        // listTimeInfo LiveData를 관찰하여 RecyclerView 업데이트
        viewModel.listTimeInfo.observe(viewLifecycleOwner) { timeListResponse ->
            timeListResponse?.let {
                adapter.timeList = it.result
                adapter.notifyDataSetChanged()
            }
        }

        binding.planSubjectListSemester.setOnClickListener {
            navigate(R.id.action_planSettingFragment_to_planSemesterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
