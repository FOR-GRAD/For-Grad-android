package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditFragment : Fragment() {
    private var _binding: FragmentCareerEditBinding? = null
    private val viewModel: CareerEditViewModel by activityViewModels()
    private lateinit var mContext: Context
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditBinding.inflate(inflater, container, false)
        _binding!!.vm = viewModel
        _binding!!.lifecycleOwner = this
        mContext = requireContext()

        _binding!!.ivCareerEditBack.setOnClickListener {
            navigate(R.id.action_fragment_career_edit_to_fragment_career)
        }

        _binding!!.etCareerEditSpinner.setOnClickListener {
            val bottomSheet = CategoryBottomFragment(mContext, viewModel)
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) { selectedCategory ->
            replaceFragment(selectedCategory)
        }

        return binding.root
    }


    private fun replaceFragment(selectedCategory: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        for (fragment in childFragmentManager.fragments) {
            fragmentTransaction.remove(fragment)
        }

        when (selectedCategory) {
            "자격증" -> fragmentTransaction.add(
                R.id.constraintLayout_career_edit,
                CareerEditCertificateFragment()
            )
            "봉사활동" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerEditVolunteerFragment()
            )
            "공모전" -> fragmentTransaction.add(
                R.id.constraintLayout_career_edit,
                CareerEditContestFragment()
            )
            "교외활동" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerEditActivityFragment()
            )
        }
        fragmentTransaction.commit()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.selectedCategory.observe(viewLifecycleOwner) { selectedCategory ->
            binding.etCareerEditSpinner.text = Editable.Factory.getInstance().newEditable(selectedCategory)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}