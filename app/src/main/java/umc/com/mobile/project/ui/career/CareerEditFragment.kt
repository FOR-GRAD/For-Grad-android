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
            // 해당 카테고리에 따라 프래그먼트를 동적으로 교체
            replaceFragment(selectedCategory)
        }

        /*viewModel.category.observe(viewLifecycleOwner, { category ->
            // Include된 레이아웃의 ID를 참조합니다.
            val includedActivity = binding.incCareerEditActivity.root
            val includedCertificate = binding.incCareerEditCertificate.root
            val includedVolunteer = binding.incCareerEditVolunteer.root
            val includedContest = binding.incCareerEditContest.root
            // 가시성을 설정합니다.
            if ("교외활동".equals(category)) {
                includedActivity.visibility = View.VISIBLE
            } else {
                includedActivity.visibility = View.GONE
            }
            if ("자격증".equals(category)) {
                includedCertificate.visibility = View.VISIBLE
            } else {
                includedCertificate.visibility = View.GONE
            }
            if ("봉사활동".equals(category)) {
                includedVolunteer.visibility = View.VISIBLE
            } else {
                includedVolunteer.visibility = View.GONE
            }
            if ("공모전".equals(category)) {
                includedContest.visibility = View.VISIBLE
            } else {
                includedContest.visibility = View.GONE
            }
        });*/

            /*        _binding!!.etCareerEditFile.setOnClickListener {
                        val bottomSheet = UploadBottomFragment(mContext)
                        bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                        bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
                    }

                    _binding!!.etCareerEditYear.setOnClickListener {
                        val bottomSheet = PeriodBottomFragment(mContext, viewModel)
                        bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                        bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
                    }

                    _binding!!.btnCareerEditAdd.setOnClickListener {
                        navigate(R.id.action_fragment_career_edit_to_fragment_career_confirm)
                    }*/

        return binding.root
    }


    private fun replaceFragment(selectedCategory: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        // 모든 프래그먼트를 제거
        for (fragment in childFragmentManager.fragments) {
            fragmentTransaction.remove(fragment)
        }

        // 선택한 카테고리에 따라 프래그먼트 추가
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
/*        viewModel.selectedYear.observe(viewLifecycleOwner) { selectedYear ->
            binding.etCareerEditYear.text = Editable.Factory.getInstance().newEditable("${selectedYear}년")
        }
        viewModel.selectedMonth.observe(viewLifecycleOwner) { selectedMonth ->
            binding.etCareerEditMonth.text = Editable.Factory.getInstance().newEditable("${selectedMonth}월")
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) { selectedDay ->
            binding.etCareerEditDay.text = Editable.Factory.getInstance().newEditable("${selectedDay}일")
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}