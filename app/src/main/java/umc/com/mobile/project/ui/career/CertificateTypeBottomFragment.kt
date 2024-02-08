package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentCertificateTypeBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddCertificateViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditCertificateViewModel

class CertificateTypeBottomFragment(
    context: Context,
    private val viewModelType: Int
) : BottomSheetDialogFragment() {
    private var _binding: FragmentCertificateTypeBottomBinding? = null
    private val binding get() = _binding!!

    val addViewModel: CareerAddCertificateViewModel by activityViewModels()
    val editViewModel: CareerEditCertificateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCertificateTypeBottomBinding.inflate(inflater, container, false)

        val types = arrayOf(
            "실기",
            "필기",
            "면접"
        )

        var numberPicker: NumberPicker = _binding!!.npCertificateTypeBottomNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = types.size - 1

        val stringArray = types.map { it as String? }.toTypedArray()
        numberPicker.displayedValues = stringArray
        numberPicker.wrapSelectorWheel = false

        numberPicker.setOnClickListener {
            val selectedType = types[numberPicker.value]

            when (viewModelType) {
                1 -> {
                    addViewModel.updateCertificateType(selectedType)
                }
                2 -> {
                    editViewModel.updateCertificateType(selectedType)
                }
            }

            dialog!!.dismiss()
        }
        _binding!!.ivCertificateTypeBottomClose.setOnClickListener {
            dialog!!.dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}