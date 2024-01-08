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

        var numberPicker : NumberPicker = GradDateBottomBinding!!.yearpickerDatepicker
        numberPicker.minValue = 0
        numberPicker.maxValue = grad_date.size - 1
        numberPicker.displayedValues = grad_date
        numberPicker.wrapSelectorWheel = false

        GradDateBottomBinding!!.yearpickerDatepicker.setOnClickListener {
            /*GradDateBinding!!.textGradDate.text = grad_date[numberPicker.value]*/
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
}