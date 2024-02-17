package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.com.mobile.project.data.model.plan.PlanFreeRequest
import umc.com.mobile.project.databinding.FragmentPlanFreeBinding
import umc.com.mobile.project.ui.plan.PlanViewModel

class PlanFreeFragment : Fragment() {
    private var _binding: FragmentPlanFreeBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanFreeBinding.inflate(inflater, container, false)

        // 버튼 클릭 이벤트 설정
        binding.freeStoreButton.setOnClickListener {
            val memoText = binding.planFreeMemo.text.toString()
            if (memoText.isNotEmpty()) {
                viewModel.postMemo(PlanFreeRequest(memo = memoText))
            }
        }

        // 메모 저장 결과 관찰
        viewModel.postMemoResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "메모가 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show()
                viewModel.getFreeInfo() // 저장 후 메모 정보 다시 불러오기
            } else {
                Toast.makeText(context, "메모 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 저장된 메모 정보를 불러와서 EditText에 표시
        viewModel.planFreeInfo.observe(viewLifecycleOwner) { planFreeResponse ->
            planFreeResponse?.result?.memo?.let {
                binding.planFreeMemo.setText(it)
            }
        }

        binding.editMemo.setOnClickListener {
            val memoText = binding.planFreeMemo.text.toString()
            if (memoText.isNotEmpty()) {
                viewModel.editMemo(memoText) // 수정된 메모를 서버에 전송
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

