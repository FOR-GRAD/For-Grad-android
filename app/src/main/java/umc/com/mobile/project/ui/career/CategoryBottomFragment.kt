package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentCategoryBottomBinding

class CategoryBottomFragment : Fragment() {
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

/*        _binding!!.ivCategoryBottomClose.setOnClickListener {
            val dialogFragment = requireActivity().supportFragmentManager
                .findFragmentByTag("YourDialogFragmentTag") as DialogFragment?

            dialogFragment?.dismiss()
        }*/
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}