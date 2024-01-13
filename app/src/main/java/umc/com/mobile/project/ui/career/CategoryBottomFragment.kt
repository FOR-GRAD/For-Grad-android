package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentCategoryBottomBinding

class CategoryBottomFragment(context: Context) : BottomSheetDialogFragment() {
    private var _binding: FragmentCategoryBottomBinding? = null
    private val viewModel: CategoryBottomViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBottomBinding.inflate(inflater, container, false)

        /*viewModel.text.observe(viewLifecycleOwner) {
            binding.textCareer.text = it
        }*/

        val categories = arrayOf(
            getString(umc.com.mobile.project.R.string.career_certificate_title),
            getString(umc.com.mobile.project.R.string.career_contest_title),
            getString(umc.com.mobile.project.R.string.career_volunteer_title),
            "교외활동",
            "기타"
        )

        var numberPicker: NumberPicker = _binding!!.npCategoryBottomNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = categories.size - 1

        val stringArray = categories.map { it as String? }.toTypedArray()
        numberPicker.displayedValues = stringArray
        numberPicker.wrapSelectorWheel = false

        _binding!!.ivCategoryBottomClose.setOnClickListener {
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