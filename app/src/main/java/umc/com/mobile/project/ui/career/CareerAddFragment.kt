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
import umc.com.mobile.project.databinding.FragmentCareerAddBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerAddFragment : Fragment() {
    private var _binding: FragmentCareerAddBinding? = null
    private val viewModel: CareerAddViewModel by activityViewModels()
    private lateinit var mContext: Context
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerAddBinding.inflate(inflater, container, false)
        _binding!!.vm = viewModel
        _binding!!.lifecycleOwner = viewLifecycleOwner
        mContext = requireContext()

        _binding!!.ivCareerEditBack.setOnClickListener {
            navigate(R.id.action_fragment_career_add_to_fragment_career)
        }

        _binding!!.etCareerEditSpinner.setOnClickListener {
            val bottomSheet = CategoryBottomFragment(mContext, viewModel)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }

        return binding.root
    }


    private fun replaceFragment(selectedCategory: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        for (fragment in childFragmentManager.fragments) {
            fragmentTransaction.remove(fragment)
        }

        when (selectedCategory) {
            "자격증" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerAddCertificateFragment()
            )

            "봉사활동" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerAddVolunteerFragment()
            )

            "공모전" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerAddContestFragment()
            )

            "교외활동" -> fragmentTransaction.replace(
                R.id.constraintLayout_career_edit,
                CareerAddActivityFragment()
            )

            else -> {
                val existingFragment =
                    childFragmentManager.findFragmentById(R.id.constraintLayout_career_edit)
                if (existingFragment != null) {
                    fragmentTransaction.remove(existingFragment)
                }
            }
        }
        fragmentTransaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.selectedCategory.observe(viewLifecycleOwner) { selectedCategory ->
            binding.etCareerEditSpinner.text =
                Editable.Factory.getInstance().newEditable(selectedCategory)
            replaceFragment(selectedCategory)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}