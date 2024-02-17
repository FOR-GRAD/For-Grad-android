import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.data.model.plan.TrackResult
import umc.com.mobile.project.databinding.PlanTimeChooseTrackBinding
import umc.com.mobile.project.ui.plan.PlanSettingFragment
import umc.com.mobile.project.ui.plan.PlanTrackAdapter
import umc.com.mobile.project.ui.plan.PlanViewModel

class PlanTrackFragment : Fragment() {
    private var _binding: PlanTimeChooseTrackBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PlanTimeChooseTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.resetTrackSelection()

        viewModel.hakki.observe(viewLifecycleOwner) { hakkiValue ->
            if (hakkiValue.isNotEmpty()) {
                Log.d("PlanTrackFragment", "Observed hakki value: $hakkiValue")
                viewModel.getTrackInfo(hakkiValue)
            }
        }

        // 클릭 리스너 정의
        val onItemClick: (TrackResult) -> Unit = { selectedItem ->
            // 옵저빙된 'hakkiValue' 사용
            viewModel.hakki.value?.let { observedHakki ->
                // 선택된 트랙 정보를 ViewModel에 저장
                viewModel.setHakkiAndTrack(observedHakki, selectedItem.trackCode)
                // PlanSettingFragment로 화면 전환
                findNavController().navigate(R.id.action_planTrackFragment_to_planSettingFragment)
            }
        }

        // Adapter 초기화 및 RecyclerView 설정
        val adapter = PlanTrackAdapter(emptyList(), onItemClick)
        binding.recyclerViewPlanTrack.adapter = adapter
        binding.recyclerViewPlanTrack.layoutManager = LinearLayoutManager(context)

        // 트랙 정보 갱신 관찰
        viewModel.planTrackInfo.observe(viewLifecycleOwner) { trackInfo ->
            adapter.trackList = trackInfo?.result ?: emptyList()
            adapter.notifyDataSetChanged()
        }

        // 뒤로 가기 버튼 리스너
        binding.planTrackBackspace.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
