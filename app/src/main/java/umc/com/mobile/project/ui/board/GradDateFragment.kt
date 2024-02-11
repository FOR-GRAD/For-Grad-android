package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class GradDateFragment : Fragment() {
    private var _binding: FragmentGradDateBinding? = null
    private lateinit var mContext: Context
    private var bottomSheetBinding: FragmentGradDateBottomBinding? = null
    private val viewModel: GradDateViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGradDateBinding.inflate(inflater, container, false)
        mContext = requireContext()
        bottomSheetBinding =
            FragmentGradDateBottomBinding.inflate(layoutInflater)  // bottomSheetBinding 초기화

        with(binding) {
            tvGradDateDate.setOnClickListener {
                val bottomSheet = GradDateBottomFragment(mContext)
                bottomSheet.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    umc.com.mobile.project.R.style.RoundCornerBottomSheetDialogTheme
                )
                bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
            }
        }

        navigateBack() // 뒤로 가기 버튼 클릭 시
        initTodayDate() // 오늘 날짜 설정

        viewModel.dateResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null && response.result != null) {
                binding.tvGradDateDday.text = "D-" + response.result.dday.toString()
            }
        })

        binding.btnSave.setOnClickListener {
            val originalFormat = SimpleDateFormat("yyyy-M월-d", Locale.KOREA)
            val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

            val date = originalFormat.parse(viewModel.selectedDateRequest.value)
            val formattedDate = targetFormat.format(date)
            viewModel.updateCheeringMessage(binding.tvGradDateMemo.text.toString())
            viewModel.updateDateInfo(formattedDate)
            Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_LONG).show()
            navigate(R.id.action_fragment_date_to_fragment_home)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDateInfo()

        viewModel._selectedDate.observe(viewLifecycleOwner) { date ->
            binding.tvGradDateDate.text = date
        }

        viewModel.dateResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null && response.result != null) {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val outputFormat = SimpleDateFormat("졸업 예정일 yyyy'년' MM'월' dd'일'", Locale.KOREA)

                val dateStr = inputFormat.parse(response.result.gradDate)
                if (dateStr != null) {
                    val formattedDateStr = outputFormat.format(dateStr)
                    binding.tvGradDateDate.text = formattedDateStr
                }

                binding.tvGradDateDday.text = "D-" + response.result.dday.toString()
            }
        })

        viewModel.cheeringMessage.observe(viewLifecycleOwner) { message ->
            binding.tvGradDateMemo.text = Editable.Factory.getInstance().newEditable(message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateBack() {
        binding.btnBack.setOnClickListener {
            navigate(R.id.action_fragment_date_to_fragment_home)
        }
    }

    private fun initTodayDate() {
        val currentTime = LocalDateTime.now()
        val pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

        binding.tvGradDateToday.text = currentTime.format(pattern)
    }

}