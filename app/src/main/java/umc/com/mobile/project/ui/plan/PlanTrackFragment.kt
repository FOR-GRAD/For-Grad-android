import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PlanTimeChooseTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetTrackSelection() // 화면 진입 시 트랙 선택 초기화

        val hakki = arguments?.getString("hakki") ?: ""



        // 클릭 리스너 정의
        val onItemClick: (TrackResult) -> Unit = { selectedItem ->
            val bundle = Bundle().apply {
                putString("hakki", hakki)
                putString("trackId", selectedItem.trackCode)
            }
            findNavController().navigate(R.id.action_planTrackFragment_to_planTimeFragment, bundle)
        }

        // Adapter 초기화
        val adapter = PlanTrackAdapter(emptyList(), onItemClick)

        // RecyclerView 설정
        binding.recyclerViewPlanTrack.adapter = adapter
        binding.recyclerViewPlanTrack.layoutManager = LinearLayoutManager(context)

        // Track 정보 로드
        viewModel.getTrackInfo(hakki)

        // Track 정보 갱신 관찰
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
