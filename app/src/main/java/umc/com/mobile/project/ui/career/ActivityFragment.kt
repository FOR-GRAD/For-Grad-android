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
import umc.com.mobile.project.databinding.FragmentCareerActivityBinding
import umc.com.mobile.project.ui.career.adapter.CertificateRVAdapter
import umc.com.mobile.project.ui.career.viewmodel.ActivityViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ActivityFragment : Fragment() {
    private var _binding: FragmentCareerActivityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerActivityBinding.inflate(inflater, container, false)
        viewModel.getActivityInfo()
        viewModel.activityInfo.observe(viewLifecycleOwner, Observer { activityInfo ->
            val adapter = CertificateRVAdapter(activityInfo?.result!!.activityWithAccumulatedHours)
            binding.rvCareerActivityList.adapter = adapter
            binding.rvCareerActivityList.layoutManager = LinearLayoutManager(requireContext())
        })

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