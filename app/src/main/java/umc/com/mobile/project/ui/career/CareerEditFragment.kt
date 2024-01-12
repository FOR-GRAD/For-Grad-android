package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditBinding
import umc.com.mobile.project.ui.board.GradDateBottomFragment
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditFragment : Fragment() {

    private var _binding: FragmentCareerEditBinding? = null
    private val viewModel: CareerEditViewModel by viewModels()
    private lateinit var mContext: Context
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditBinding.inflate(inflater, container, false)
        mContext = requireContext()
        /*viewModel.text.observe(viewLifecycleOwner) {
            binding.textCareer.text = it
        }*/
        _binding!!.ivCareerEditBack.setOnClickListener {
            navigate(R.id.action_fragment_career_edit_to_fragment_career)
        }

        _binding!!.ivCareerEditDown.setOnClickListener {
            val bottomSheet = CategoryBottomFragment(mContext)
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}