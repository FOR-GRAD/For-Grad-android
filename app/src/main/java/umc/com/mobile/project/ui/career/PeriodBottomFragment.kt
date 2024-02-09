package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentPeriodBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel

class PeriodBottomFragment(
    context: Context,
    private val isStartDate: Boolean,
    private val viewModelType: Int
) : BottomSheetDialogFragment() {
    private var _binding: FragmentPeriodBottomBinding? = null
    private val binding get() = _binding!!
    private val addViewModel: CareerAddViewModel by activityViewModels()
    private val editViewModel: CareerEditViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeriodBottomBinding.inflate(inflater, container, false)

        val years = ArrayList<String>()
        for (year in 2015..2024) {
            years.add("$year")
        }
        val year = years.toTypedArray()

        var numberPicker_year: NumberPicker = _binding!!.npPeriodBottomYear
        numberPicker_year.minValue = 0
        numberPicker_year.maxValue = year.size - 1
        numberPicker_year.displayedValues = year
        numberPicker_year.wrapSelectorWheel = false

        val months = ArrayList<String>()
        for (month in 1..12) {
            months.add(String.format("%02d", month))
        }
        val month = months.toTypedArray()

        var numberPicker_month: NumberPicker = _binding!!.npPeriodBottomMonth
        numberPicker_month.minValue = 0
        numberPicker_month.maxValue = month.size - 1
        numberPicker_month.displayedValues = month
        numberPicker_month.wrapSelectorWheel = false

        val days = ArrayList<String>()
        for (day in 1..31) {
            days.add(String.format("%02d", day))
        }
        val day = days.toTypedArray()

        var numberPicker_day: NumberPicker = _binding!!.npPeriodBottomDay
        numberPicker_day.minValue = 0
        numberPicker_day.maxValue = day.size - 1
        numberPicker_day.displayedValues = day
        numberPicker_day.wrapSelectorWheel = false

        //선택된 년도, 월, 일 가져오기
        val selectedYear = if (viewModelType == 1) {
            if (isStartDate) addViewModel.selectedStartYear.value else addViewModel.selectedEndYear.value
        } else {
            if (isStartDate) editViewModel.selectedStartYear.value else editViewModel.selectedEndYear.value
        }

        val selectedMonth = if (viewModelType == 1) {
            if (isStartDate) addViewModel.selectedStartMonth.value else addViewModel.selectedEndMonth.value
        } else {
            if (isStartDate) editViewModel.selectedStartMonth.value else editViewModel.selectedEndMonth.value
        }

        val selectedDay = if (viewModelType == 1) {
            if (isStartDate) addViewModel.selectedStartDay.value else addViewModel.selectedEndDay.value
        } else {
            if (isStartDate) editViewModel.selectedStartDay.value else editViewModel.selectedEndDay.value
        }

        //년도 설정
        val selectedYearIndex = years.indexOf(selectedYear.toString())
        numberPicker_year.value = if (selectedYearIndex != -1) selectedYearIndex else 0

        //월 설정
        val selectedMonthIndex = months.indexOf(selectedMonth.toString())
        numberPicker_month.value = if (selectedMonthIndex != -1) selectedMonthIndex else 0

        //일 설정
        val selectedDayIndex = days.indexOf(selectedDay.toString())
        numberPicker_day.value = if (selectedDayIndex != -1) selectedDayIndex else 0

        _binding!!.npPeriodBottomYear.setOnClickListener {
            val selectedYear = years[numberPicker_year.value]
            if (viewModelType == 1) {
                addViewModel.updateSelectedYear(selectedYear, isStartDate)
            } else {
                editViewModel.updateSelectedYear(selectedYear, isStartDate)
            }
            dialog!!.dismiss()
        }
        _binding!!.npPeriodBottomMonth.setOnClickListener {
            val selectedMonth = months[numberPicker_month.value]
            if (viewModelType == 1) {
                addViewModel.updateSelectedMonth(selectedMonth, isStartDate)
            } else {
                editViewModel.updateSelectedMonth(selectedMonth, isStartDate)
            }
            dialog!!.dismiss()
        }
        _binding!!.npPeriodBottomDay.setOnClickListener {
            val selectedDay = days[numberPicker_day.value]
            if (viewModelType == 1) {
                addViewModel.updateSelectedDay(selectedDay, isStartDate)
            } else {
                editViewModel.updateSelectedDay(selectedDay, isStartDate)
            }
            dialog!!.dismiss()
        }
        _binding!!.ivPeriodBottomClose.setOnClickListener {
            dialog!!.dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}