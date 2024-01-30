package umc.com.mobile.project.ui.career

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentCareerNonsubjectBinding
import umc.com.mobile.project.ui.career.adapter.NonSubjectRVAdapter
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
        _binding!!.ivCareerNonsubjectBack.setOnClickListener {
            navigate(R.id.action_fragment_nonsubject_to_fragment_career)
        }
        viewModel.getNonSubjectInfo2(1)// 비교과 조회 api
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
        _binding!!.ivCareerLeftArrow.setOnClickListener() {
            if (viewModel.currentPage > 1){
                loadPreviousPage()
            }
            else {
                Toast.makeText(requireContext(), "첫 페이지입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        _binding!!.ivCareerRightArrow.setOnClickListener() {
            if (viewModel.currentPage < viewModel.pageSize){
                loadNextPage()
            }
            else {
                Toast.makeText(requireContext(), "마지막 페이지입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loadPreviousPage() {
        viewModel.getNonSubjectInfo2(viewModel.currentPage - 1)
        viewModel.nonSubjectInfo.observe(viewLifecycleOwner, { nonSubjectResponse ->
            nonSubjectResponse?.result.let { result ->
                val adapter = NonSubjectRVAdapter(result)
                binding.rvCareerNonsubjectList.adapter = adapter
                binding.rvCareerNonsubjectList.layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
    private fun loadNextPage() {
        viewModel.getNonSubjectInfo2(viewModel.currentPage + 1)
        viewModel.nonSubjectInfo.observe(viewLifecycleOwner, { nonSubjectResponse ->
            nonSubjectResponse?.result.let { result ->
                val adapter = NonSubjectRVAdapter(result)
                binding.rvCareerNonsubjectList.adapter = adapter
                binding.rvCareerNonsubjectList.layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}