package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditActivityBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditActivityFragment : Fragment() {
    private var _binding: FragmentCareerEditActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private val viewModel: CareerEditActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditActivityBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        mContext = requireContext()

        _binding!!.ivCareerActivityBack.setOnClickListener {
            navigate(R.id.action_fragment_edit_activity_to_fragment_career_activity)
        }
        _binding!!.etCareerEditActivityFile.setOnClickListener {
            val bottomSheet = UploadBottomFragment(mContext, 2)
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
        viewModel.getActivityDetail()
        // 불러온 값 hint에 넣어줌
        viewModel.activityDetailInfo.observe(viewLifecycleOwner) { _activityInfo ->
            _activityInfo?.let {
                _binding?.etCareerEditActivity?.hint = it.result.title ?: ""
                _binding?.etCareerEditActivityFile?.hint =
                    it.result.fileUrls?.toString() ?: ""
                _binding?.etCareerEditActivityStartYear?.hint = it.result.startDate ?: ""
                _binding?.etCareerEditActivityEndYear?.hint = it.result.endDate ?: ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}