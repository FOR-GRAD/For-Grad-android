package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditContestBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditContestViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditContestFragment : Fragment() {
    private var _binding: FragmentCareerEditContestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CareerEditContestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditContestBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        _binding!!.ivCareerContestBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_contest_to_fragment_career_contest)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}