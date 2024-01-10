package umc.com.mobile.project.ui.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import umc.com.mobile.project.databinding.FragmentGradDateBinding
import umc.com.mobile.project.databinding.FragmentGradDateBottomBinding

class GradDateFragment : Fragment() {
    private var _binding: FragmentGradDateBinding? = null
    private lateinit var mContext: Context
    private var bottomSheetBinding: FragmentGradDateBottomBinding? = null
    private val viewModel: GradDateViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGradDateBinding.inflate(inflater, container, false)
        mContext = requireContext()
        bottomSheetBinding = FragmentGradDateBottomBinding.inflate(layoutInflater)  // bottomSheetBinding 초기화
        /*viewModel.text.observe(viewLifecycleOwner) {
            binding.textBoard.text = it
        }*/
        with(binding) {
            tvGradDateDate.setOnClickListener {
                val bottomSheet = GradDateBottomFragment(mContext)
                bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, umc.com.mobile.project.R.style.RoundCornerBottomSheetDialogTheme)
                bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
            }
        }
        //실패 코드-값을 눌러도 text 안 바뀜
        viewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            binding.tvGradDateDate.text = selectedDate
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}