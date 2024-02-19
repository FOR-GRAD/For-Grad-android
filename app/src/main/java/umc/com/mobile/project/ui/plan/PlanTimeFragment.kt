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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PlanSubjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupNavigation()
    }

    private fun setupRecyclerView() {
        val adapter = PlanRecyclerAdapter(emptyList(), onAddButtonClicked = { timeResult ->

            if (timeResult != null) {
                viewModel.setSelectedTimeResult(timeResult)
            }


        })

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        // 학기(hakki)와 트랙(trackId) 정보 변경 감지
        viewModel.hakki.observe(viewLifecycleOwner) { hakki ->
            viewModel.track.value?.let { trackId ->
                if (hakki.isNotEmpty() && trackId.isNotEmpty()) {
                    viewModel.getListTimeInfo(hakki, trackId)
                }
            }
        }

        // listTimeInfo LiveData 관찰
        viewModel.listTimeInfo.observe(viewLifecycleOwner) { timeListResponse ->
            (binding.recyclerView.adapter as PlanRecyclerAdapter).updateTimeList(timeListResponse?.result ?: emptyList())
        }
    }

    private fun setupNavigation() {
        binding.planTimeMoveTimetable.setOnClickListener {
            navigate(R.id.action_planSettingFragment_to_planTimetableFragment)
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
