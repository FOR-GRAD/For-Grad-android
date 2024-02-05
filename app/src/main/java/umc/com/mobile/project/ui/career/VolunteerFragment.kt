package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerVolunteerBinding
import umc.com.mobile.project.ui.career.adapter.VolunteerRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.CareerEditVolunteerViewModel
import umc.com.mobile.project.ui.career.viewmodel.VolunteerViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class VolunteerFragment : Fragment() {
    private var _binding: FragmentCareerVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VolunteerViewModel by viewModels()
    private val sharedViewModel: CareerEditVolunteerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerVolunteerBinding.inflate(inflater, container, false)

        // adapter 초기화
        val adapter = VolunteerRVAdapter(emptyList())
        binding.rvCareerVolunteerList.adapter = adapter
        binding.rvCareerVolunteerList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getVolunteerInfo()
        viewModel.volunteerInfo.observe(viewLifecycleOwner, Observer { volunteerInfo ->
            adapter.updateItems(volunteerInfo?.result!!.activityWithAccumulatedHours)

            adapter.setOnItemClickListener(object : VolunteerRVAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    sharedViewModel.studentId.value =
                        volunteerInfo?.result!!.activityWithAccumulatedHours[position].id
                    navigate(R.id.action_fragment_volunteer_to_fragment_career_edit_volunteer)
                }
            })
            adapter.notifyDataSetChanged()
        })

        _binding!!.ivCareerVolunteerSearch.setOnClickListener {
            viewModel.searchVolunteerInfo(_binding!!.etCareerVolunteerSearchBar.text.toString())
            viewModel.searchInfo.observe(viewLifecycleOwner, Observer { searchInfo ->
                adapter.updateItems(searchInfo?.result!!.activityWithAccumulatedHours)
                adapter.setOnItemClickListener(object : VolunteerRVAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        sharedViewModel.studentId.value =
                            searchInfo?.result!!.activityWithAccumulatedHours[position].id
                        navigate(R.id.action_fragment_volunteer_to_fragment_career_edit_volunteer)
                    }
                })
                adapter.notifyDataSetChanged()
            })
        }

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