package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.databinding.SemesterChooseBinding

import umc.com.mobile.project.R
import umc.com.mobile.project.ui.plan.adapter.PlanSemesterAdapter
import umc.com.mobile.project.ui.plan.viewmodel.PlanViewModel


class PlanSemesterFragment : Fragment() {
    private var _binding: SemesterChooseBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SemesterChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.resetSemesterSelection() // 상태 초기화

        val adapter = PlanSemesterAdapter(emptyList()) { selectedItem ->
            // ViewModel을 통해 hakki 값을 설정
            viewModel.setHakki(selectedItem.hakkiNum)

            // PlanTrackFragment로 화면 전환. Bundle 대신 ViewModel 사용
            findNavController().navigate(R.id.action_planSemesterFragment_to_planTrackFragment)
        }

        binding.recyclerViewPlanSemester.adapter = adapter
        binding.recyclerViewPlanSemester.layoutManager = LinearLayoutManager(context)

        viewModel.planSemesterInfo.observe(viewLifecycleOwner) { semesterInfo ->
            adapter.semesterList = semesterInfo?.result ?: emptyList()
            adapter.notifyDataSetChanged()
        }

        viewModel.getSemesterInfo() // 데이터 로드

        binding.planSemesterBackspace.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
