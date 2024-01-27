package umc.com.mobile.project.ui.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerNonsubjectBinding
import umc.com.mobile.project.ui.career.adapter.NonSubjectRVAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class NonSubjectFragment : Fragment() {
    private var _binding: FragmentCareerNonsubjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareerNonsubjectBinding.inflate(inflater, container, false)
        val certificates = arrayListOf(
            CertificateDto("2022-01-01", "자격증1", "필기", "1급"),
            CertificateDto("2022-02-15", "자격증2", "필기", "1급"),
            CertificateDto("2022-03-30", "자격증3", "실기", "1급"),
            CertificateDto("2022-02-15", "자격증4", "필기", "1급"),
            CertificateDto("2022-03-30", "자격증5", "실기", "1급")
        )
        val adapter = NonSubjectRVAdapter(certificates)
        binding.rvCareerNonsubjectList.adapter = adapter
        binding.rvCareerNonsubjectList.layoutManager = LinearLayoutManager(requireContext())

        _binding!!.ivCareerNonsubjectBack.setOnClickListener {
            navigate(R.id.action_fragment_nonsubject_to_fragment_career)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}