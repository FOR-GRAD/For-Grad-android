package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerVolunteerBinding
import umc.com.mobile.project.ui.career.adapter.VolunteerRVAdapter
import umc.com.mobile.project.ui.career.data.VolunteerDto
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class VolunteerFragment : Fragment() {
    private var _binding: FragmentCareerVolunteerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerVolunteerBinding.inflate(inflater, container, false)
        val volunteers = arrayListOf(
            VolunteerDto("2022-01-01", "봉사활동1", "4h", "4h"),
            VolunteerDto("2022-02-15", "봉사활동2", "4h", "8h"),
            VolunteerDto("2022-03-30", "봉사활동3", "4h", "12h"),
            VolunteerDto("2022-02-15", "봉사활동4", "4h", "16h"),
            VolunteerDto("2022-03-30", "봉사활동5", "4h", "20h")
        )
        val adapter = VolunteerRVAdapter(volunteers)
        binding.rvCareerVolunteerList.adapter = adapter
        binding.rvCareerVolunteerList.layoutManager = LinearLayoutManager(requireContext())

        _binding!!.ivCareerVolunteerBack.setOnClickListener {
            navigate(R.id.action_fragment_volunteer_to_fragment_career)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}