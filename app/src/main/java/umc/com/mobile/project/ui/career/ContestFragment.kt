package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerContestBinding
import umc.com.mobile.project.ui.career.adapter.CertificateRVAdapter
import umc.com.mobile.project.ui.career.adapter.ContestRVAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.data.ContestDto
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class ContestFragment : Fragment() {
    private var _binding: FragmentCareerContestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerContestBinding.inflate(inflater, container, false)
        val contests = arrayListOf(
            ContestDto("2022-01-01", "공모전 이름1", "필기", "상 이름"),
            ContestDto("2022-02-15", "공모전 이름2", "필기", "상 이름"),
            ContestDto("2022-03-30", "공모전 이름3", "실기", "상 이름"),
            ContestDto("2022-02-15", "공모전 이름4", "필기", "상 이름"),
            ContestDto("2022-03-30", "공모전 이름5", "실기", "상 이름")
        )
        val adapter = ContestRVAdapter(contests)
        binding.rvCareerContestList.adapter = adapter
        binding.rvCareerContestList.layoutManager = LinearLayoutManager(requireContext())

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