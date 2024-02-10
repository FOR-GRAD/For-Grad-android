package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel

/*
class GradDateBottomFragment(context: Context) : BottomSheetDialogFragment() {

    private var GradDateBottomBinding: FragmentGradDateBottomBinding? = null
    private val binding get() = GradDateBottomBinding!!
    private var GradDateBinding: FragmentGradDateBinding? = null
    private val GradDateViewModel: GradDateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        GradDateBottomBinding = FragmentGradDateBottomBinding.inflate(inflater, container, false)
        GradDateBinding = FragmentGradDateBinding.inflate(inflater, container, false)

        val gradDates = ArrayList<String>()
        for (year in 2024..2029) {
            gradDates.add("$year 년 2월")
            gradDates.add("$year 년 8월")
        }
        val grad_date = gradDates.toTypedArray()

        var numberPicker : NumberPicker = GradDateBottomBinding!!.npGradDateNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = grad_date.size - 1
        numberPicker.displayedValues = grad_date
        numberPicker.wrapSelectorWheel = false

        GradDateBottomBinding!!.ivGradDateClose.setOnClickListener {
            */
/*GradDateBinding!!.textGradDate.text = grad_date[numberPicker.value]*//*

            val selectedDate = grad_date[numberPicker.value]
            GradDateViewModel.selectedDate.value = selectedDate
            dialog!!.dismiss()
            dialog!!.cancel()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        GradDateBottomBinding = null
    }
}*/
class GradDateBottomFragment(context: Context) : BottomSheetDialogFragment() {

	private var gradDateBottomBinding: FragmentGradDateBottomBinding? = null
	private val binding get() = gradDateBottomBinding!!
	private var gradDateBinding: FragmentGradDateBinding? = null
	private val gradDateViewModel: GradDateViewModel by viewModels()

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
		val years = (2024..2050).map { it.toString() }.toTypedArray()
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
			val selectedYear = years[yearPicker.value]
			val selectedMonth = months[monthPicker.value]
			val selectedDay = days[dayPicker.value]
			gradDateViewModel.selectedDate.value = "$selectedYear $selectedMonth $selectedDay"
			dialog!!.dismiss()
			dialog!!.cancel()
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
