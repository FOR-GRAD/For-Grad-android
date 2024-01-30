package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.R
import umc.com.mobile.project.data.network.api.CareerApi
import umc.com.mobile.project.databinding.FragmentCareerNonsubjectBinding
import umc.com.mobile.project.ui.career.adapter.NonSubjectRVAdapter
import umc.com.mobile.project.ui.career.data.CertificateDto
import umc.com.mobile.project.ui.career.viewmodel.NonSubjectViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate

class NonSubjectFragment : Fragment() {
    private var _binding: FragmentCareerNonsubjectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NonSubjectViewModel by viewModels()

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

        _binding!!.ivCareerNonsubjectBack.setOnClickListener {
            navigate(R.id.action_fragment_nonsubject_to_fragment_career)
        }
        viewModel.getNonSubjectInfo()// 비교과 조회 api
        //비교과 포인트 띄우기
        viewModel.nonSubjectInfo.observe(viewLifecycleOwner, { nonSubjectResponse ->
            nonSubjectResponse?.result?.pointSummaryDto?.let { pointSummaryDto ->
                binding.etCareerNonsubjectContent1.text = Editable.Factory.getInstance().newEditable(pointSummaryDto.semesterPoints)
                binding.etCareerNonsubjectContent2.text = Editable.Factory.getInstance().newEditable(pointSummaryDto.carryoverPoints)
                binding.etCareerNonsubjectContent3.text = Editable.Factory.getInstance().newEditable(pointSummaryDto.accumulatedPoints)
                binding.etCareerNonsubjectContent4.text = Editable.Factory.getInstance().newEditable(pointSummaryDto.graduationPoints)
            }
            nonSubjectResponse?.result.let { result ->
                val adapter = NonSubjectRVAdapter(result)
                binding.rvCareerNonsubjectList.adapter = adapter
                binding.rvCareerNonsubjectList.layoutManager = LinearLayoutManager(requireContext())
            }
        })

/*        viewModel.certificates.observe(viewLifecycleOwner, { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })*/

/*        _binding!!.rvCareerNonsubjectList.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount
                    if (lastVisibleItemPosition >= itemTotalCount - 1) {
                        if (page * 10 < totalItemCount) {
                            page++
                            requestDTO.pages = page
                            myFunction(requestDTO)
                        }
                    }
                }
            })
        }*/
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}