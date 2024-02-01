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
import umc.com.mobile.project.databinding.FragmentCareerContestBinding
import umc.com.mobile.project.ui.career.adapter.VolunteerRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.ContestViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ContestFragment : Fragment() {
    private var _binding: FragmentCareerContestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerContestBinding.inflate(inflater, container, false)
        
        //api 연결
        viewModel.getContestInfo()
        // Observe the contestInfo LiveData in ViewModel
        viewModel.contestInfo.observe(viewLifecycleOwner, Observer { contestInfo ->
            val adapter = VolunteerRVAdapter(contestInfo?.result!!.activityWithAccumulatedHours)
            binding.rvCareerContestList.adapter = adapter
            binding.rvCareerContestList.layoutManager = LinearLayoutManager(requireContext())
        })

        _binding!!.ivCareerContestBack.setOnClickListener {
            navigate(R.id.action_fragment_contest_to_fragment_career)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}