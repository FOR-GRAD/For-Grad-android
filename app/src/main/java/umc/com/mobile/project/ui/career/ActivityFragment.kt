package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerActivityBinding
import umc.com.mobile.project.ui.career.adapter.CertificateRVAdapter
import umc.com.mobile.project.ui.career.adapter.ActivityRVAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.data.ActivityDto
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ActivityFragment : Fragment() {
    private var _binding: FragmentCareerActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerActivityBinding.inflate(inflater, container, false)
        val activities = arrayListOf(
            ActivityDto("2022-01-01", "활동 이름1", "10pt", "10pt"),
            ActivityDto("2022-02-15", "활동 이름2", "10pt", "20pt"),
            ActivityDto("2022-03-30", "활동 이름3", "10pt", "30pt"),
            ActivityDto("2022-02-15", "활동 이름4", "10pt", "40pt"),
            ActivityDto("2022-03-30", "활동 이름5", "10pt", "50pt")
        )
        val adapter = ActivityRVAdapter(activities)
        binding.rvCareerActivityList.adapter = adapter
        binding.rvCareerActivityList.layoutManager = LinearLayoutManager(requireContext())

        _binding!!.ivCareerActivityBack.setOnClickListener {
            navigate(R.id.action_fragment_activity_to_fragment_career)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}