package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.databinding.SemesterChooseBinding

import umc.com.mobile.project.R


class PlanSemesterFragment : Fragment() {
    private var _binding: SemesterChooseBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SemesterChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.resetSemesterSelection() // 상태 초기화

        val adapter = PlanSemesterAdapter(emptyList()) { selectedItem ->


            val bundle = Bundle().apply {
                putString("hakki", selectedItem.hakkiNum)
            }
            findNavController().navigate(R.id.action_planSemesterFragment_to_planTrackFragment, bundle)
        }


        // RecyclerView에 어댑터와 레이아웃 매니저 설정
        binding.recyclerViewPlanSemester.adapter = adapter
        binding.recyclerViewPlanSemester.layoutManager = LinearLayoutManager(context)

        viewModel.planSemesterInfo.observe(viewLifecycleOwner) { semesterInfo ->
            // 데이터가 업데이트되면 어댑터의 리스트 업데이트
            adapter.semesterList = semesterInfo?.result ?: emptyList()
            adapter.notifyDataSetChanged()
        }

        viewModel.getSemesterInfo() // 데이터 로드


        // x 버튼 누르고 뒤로가기
         binding.planSemesterBackspace.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
