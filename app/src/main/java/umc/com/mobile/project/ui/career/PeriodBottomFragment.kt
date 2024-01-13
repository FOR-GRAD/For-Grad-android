package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentPeriodBottomBinding

class PeriodBottomFragment (context: Context) : BottomSheetDialogFragment() {
    private var _binding: FragmentPeriodBottomBinding? = null
    private val viewModel: PeriodBottomViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeriodBottomBinding.inflate(inflater, container, false)

        /*viewModel.text.observe(viewLifecycleOwner) {
            binding.textCareer.text = it
        }*/
        val years = ArrayList<String>()
        for (year in 2017..2024) {
            years.add("$year")
        }
        val year = years.toTypedArray()

        var numberPicker_year : NumberPicker = _binding!!.npPeriodBottomYear
        numberPicker_year.minValue = 0
        numberPicker_year.maxValue = year.size - 1
        numberPicker_year.displayedValues = year
        numberPicker_year.wrapSelectorWheel = false

        val months = ArrayList<String>()
        for (month in 1..12) {
            months.add("$month")
        }
        val month = months.toTypedArray()

        var numberPicker_month : NumberPicker = _binding!!.npPeriodBottomMonth
        numberPicker_month.minValue = 0
        numberPicker_month.maxValue = month.size - 1
        numberPicker_month.displayedValues = month
        numberPicker_month.wrapSelectorWheel = false

        val days = ArrayList<String>()
        for (day in 1..31) {
            days.add("$day")
        }
        val day = days.toTypedArray()

        var numberPicker_day : NumberPicker = _binding!!.npPeriodBottomDay
        numberPicker_day.minValue = 0
        numberPicker_day.maxValue = day.size - 1
        numberPicker_day.displayedValues = day
        numberPicker_day.wrapSelectorWheel = false

        _binding!!.ivPeriodBottomClose.setOnClickListener {
            dialog!!.dismiss()
            dialog!!.cancel()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}