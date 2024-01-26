package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentAwardBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditContestViewModel

class AwardBottomFragment (context: Context) : BottomSheetDialogFragment() {
    private var _binding: FragmentAwardBottomBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CareerEditContestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAwardBottomBinding.inflate(inflater, container, false)

        val awards = arrayOf(
            "대상",
            "최우수상",
            "우수상",
            "장려상"
        )

        var numberPicker: NumberPicker = _binding!!.npAwardBottomNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = awards.size - 1

        val stringArray = awards.map { it as String? }.toTypedArray()
        numberPicker.displayedValues = stringArray
        numberPicker.wrapSelectorWheel = false

        _binding!!.npAwardBottomNumberPicker.setOnClickListener {
            val selectedAward = awards[numberPicker.value]
            sharedViewModel.updateSelectedAward(selectedAward)
            dialog!!.dismiss()
        }
        _binding!!.ivAwardBottomClose.setOnClickListener {
            dialog!!.dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}