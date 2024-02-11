package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel

class GradDateBottomFragment(context: Context) : BottomSheetDialogFragment() {

    private var gradDateBottomBinding: FragmentGradDateBottomBinding? = null
    private val binding get() = gradDateBottomBinding!!
    private var gradDateBinding: FragmentGradDateBinding? = null
    private val gradDateViewModel: GradDateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        gradDateBottomBinding = FragmentGradDateBottomBinding.inflate(inflater, container, false)
        gradDateBinding = FragmentGradDateBinding.inflate(inflater, container, false)

        val yearPicker: NumberPicker = binding.npGradDateYear
        val monthPicker: NumberPicker = binding.npGradDateMonth
        val dayPicker: NumberPicker = binding.npGradDateDay

        // 년도 설정
        val years = (2020..2050).map { it.toString() }.toTypedArray()
        yearPicker.minValue = 0
        yearPicker.maxValue = years.size - 1
        yearPicker.displayedValues = years
        yearPicker.wrapSelectorWheel = false

        // 월 설정
        val months = arrayOf("2월", "8월")
        monthPicker.minValue = 0
        monthPicker.maxValue = months.size - 1
        monthPicker.displayedValues = months
        monthPicker.wrapSelectorWheel = false

        // 일 설정
        val days = (1..31).map { it.toString() }.toTypedArray()
        dayPicker.minValue = 0
        dayPicker.maxValue = days.size - 1
        dayPicker.displayedValues = days
        dayPicker.wrapSelectorWheel = false

        binding.ivGradDateClose.setOnClickListener {
//			val calendar = Calendar.getInstance()
//			calendar.set(selectedYear, selectedMonth - 1, selectedDay)
//			val selectedDate = calendar.time // 선택된 값들로 Date 생성
//
//			gradDateViewModel.selectedDate.value = selectedDate

            dialog!!.dismiss()
        }

        binding.npGradDateYear.setOnClickListener {
            val selectedYear = years[yearPicker.value]
            val selectedMonth = months[monthPicker.value]
            val selectedDay = days[dayPicker.value]

            gradDateViewModel.updateSelectedDate(selectedYear, selectedMonth, selectedDay)

            dialog!!.dismiss()
        }

        binding.npGradDateMonth.setOnClickListener {
            val selectedYear = years[yearPicker.value]
            val selectedMonth = months[monthPicker.value]
            val selectedDay = days[dayPicker.value]

            gradDateViewModel.updateSelectedDate(selectedYear, selectedMonth, selectedDay)

            dialog!!.dismiss()
        }

        binding.npGradDateDay.setOnClickListener {
            val selectedYear = years[yearPicker.value]
            val selectedMonth = months[monthPicker.value]
            val selectedDay = days[dayPicker.value]

            gradDateViewModel.updateSelectedDate(selectedYear, selectedMonth, selectedDay)

            dialog!!.dismiss()
        }

        binding.npGradDateYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npGradDateDay.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npGradDateMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gradDateBottomBinding = null
    }
}
