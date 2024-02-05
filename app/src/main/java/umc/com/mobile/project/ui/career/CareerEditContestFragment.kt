package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditContestBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditContestViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditContestFragment : Fragment() {
    private var _binding: FragmentCareerEditContestBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private val viewModel: CareerEditContestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditContestBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        mContext = requireContext()

        _binding!!.ivCareerContestBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_contest_to_fragment_career_contest)
        }
        _binding!!.etCareerEditContestAward.setOnClickListener {
            val bottomSheet = AwardBottomFragment(mContext, 2)
            bottomSheet.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //API 연결
        viewModel.getContestDetail()
        // 불러온 값 hint에 넣어줌
        viewModel.contestDetailInfo.observe(viewLifecycleOwner) { _contestInfo ->
            _contestInfo?.let {
                _binding?.etCareerEditContest?.hint = it.result.title ?: ""
                _binding?.etCareerEditContestAward?.hint =
                    it.result.award?.toString() ?: ""
                _binding?.etCareerEditContestStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditContestEndYear?.hint = it.result.endDate ?: ""
            }
        }
        viewModel.init()
        viewModel.award.observe(viewLifecycleOwner) { award ->
            binding.etCareerEditContestAward.text =
                Editable.Factory.getInstance().newEditable(award)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}