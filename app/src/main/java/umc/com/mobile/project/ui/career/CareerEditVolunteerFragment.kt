package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerEditVolunteerBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditVolunteerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class CareerEditVolunteerFragment : Fragment() {
    private var _binding: FragmentCareerEditVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CareerEditVolunteerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerEditVolunteerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //API 연결
        viewModel.getVolunteerDetail()
        //불러온 값 hint에 넣어줌
        val volunteerDetail = viewModel.volunteerDetailInfo.value?.result
        _binding!!.etCareerEditVolunteer.hint = volunteerDetail?.title
        _binding!!.etCareerEditVolunteerHour.hint = volunteerDetail?.volunteerHour.toString()
        _binding!!.etCareerEditVolunteerStartYear.hint = volunteerDetail?.startDate!!
        _binding!!.etCareerEditVolunteerEndYear.hint = volunteerDetail?.endDate!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}