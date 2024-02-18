package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import java.text.ParseException
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
        binding.vm = viewModel
        mContext = requireContext()
        bottomSheetBinding =
            FragmentGradDateBottomBinding.inflate(layoutInflater)  // bottomSheetBinding 초기화

        //fragment 들어왔을 때 수정하기 text만 보이게
        viewModel.init()

        //졸업예정일 누르면 bottom frag 뜨게
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

        binding.btnSave.setOnClickListener {
            val originalFormat = SimpleDateFormat("yyyy-M월-d", Locale.KOREA)
            val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

            val dateString = viewModel.selectedDateRequest.value
            if (dateString != null) {
                try {
                    val date = originalFormat.parse(dateString)
                    val formattedDate = targetFormat.format(date)
                    viewModel.updateDateInfo(formattedDate)
                    //Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_LONG).show()
                    navigate(R.id.action_fragment_date_to_fragment_home)
                    viewModel.onEditButtonClick()
                } catch (e: ParseException) {
                    Toast.makeText(context, "날짜를 올바른 형식으로 입력해주세요.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "졸업예정일을 선택해주세요.", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //api 연결
        viewModel.getDateInfo()

        //전에 저장했던 졸업일자랑 dday 불러오기
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

        //다시 선택한 졸업 일자 띄우기
        viewModel._selectedDate.observe(viewLifecycleOwner) { date ->
            binding.tvGradDateDate.text = date
        }

        //다시 선택한 졸업 일자에 맞춰 dday 띄우기
        viewModel._dday.observe(viewLifecycleOwner) { dday ->
            binding.tvGradDateDday.text = "D-$dday"
        }

        //api에서 메모 hint로 가져오기
        viewModel._dateResponse2.observe(viewLifecycleOwner) { response ->
            binding.tvGradDateMemo.hint = response!!.result.message
        }

        //수정하기 및 버튼 visible 조건
        viewModel.isEditMode.observe(viewLifecycleOwner, Observer { isEditMode ->
            if (isEditMode) {
                binding.tvGradDateEdit.visibility = View.GONE
                binding.btnSave.visibility = View.VISIBLE
                binding.tvGradDateDate.isEnabled = true
                binding.tvGradDateMemo.isEnabled = true
            } else {
                binding.tvGradDateEdit.visibility = View.VISIBLE
                binding.btnSave.visibility = View.GONE
                binding.tvGradDateDate.isEnabled = false
                binding.tvGradDateMemo.isEnabled = false
            }
        })

        //버튼 활성화 조건
        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            if (isEnabled) {
                binding.btnSave.apply {
                    this.isEnabled = true
                    this.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.white)
                    this.setTextColor(ContextCompat.getColor(context, R.color.skyBlue))
                }
            } else {
                binding.btnSave.apply {
                    this.isEnabled = false
                    this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray)
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
        })
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