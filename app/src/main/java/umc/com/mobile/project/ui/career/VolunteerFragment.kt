package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerVolunteerBinding
import umc.com.mobile.project.ui.career.adapter.VolunteerRVAdapter
import umc.com.mobile.project.ui.career.data.VolunteerDto
import umc.com.mobile.project.ui.career.viewmodel.NonSubjectViewModel
import umc.com.mobile.project.ui.career.viewmodel.VolunteerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class VolunteerFragment : Fragment() {
    private var _binding: FragmentCareerVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerVolunteerBinding.inflate(inflater, container, false)

        // Observe the volunteerInfo LiveData in ViewModel
        viewModel.volunteerInfo.observe(viewLifecycleOwner, Observer { volunteerInfo ->
            // Handle changes in the volunteerInfo data
            if (volunteerInfo != null) {
                // Update your RecyclerView adapter with the data
                val adapter = VolunteerRVAdapter(volunteerInfo.result.activityWithAccumulatedHours)
                binding.rvCareerVolunteerList.adapter = adapter
                binding.rvCareerVolunteerList.layoutManager = LinearLayoutManager(requireContext())
            } else {
                // Handle the case when volunteerInfo is null
            }
        })

        // Observe the error LiveData in ViewModel
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Handle errors
            // You might want to show an error message to the user
        })

/*        val volunteers = arrayListOf(
            VolunteerDto("2022-01-01", "봉사활동1", "4h", "4h"),
            VolunteerDto("2022-02-15", "봉사활동2", "4h", "8h"),
            VolunteerDto("2022-03-30", "봉사활동3", "4h", "12h"),
            VolunteerDto("2022-02-15", "봉사활동4", "4h", "16h"),
            VolunteerDto("2022-03-30", "봉사활동5", "4h", "20h")
        )
        val adapter = VolunteerRVAdapter(volunteers)*/
/*        binding.rvCareerVolunteerList.adapter = adapter
        binding.rvCareerVolunteerList.layoutManager = LinearLayoutManager(requireContext())*/

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