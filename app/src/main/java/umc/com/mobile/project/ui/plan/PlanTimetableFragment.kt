package umc.com.mobile.project.ui.plan

import PlanTimeAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.databinding.PlanTimeMainBinding
import umc.com.mobile.project.data.model.plan.AddTimeRequest
import umc.com.mobile.project.data.model.plan.SemesterDto
import umc.com.mobile.project.data.model.plan.SubjectDtoList

class PlanTimetableFragment : Fragment() {

    private var _binding: PlanTimeMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlanViewModel by activityViewModels()
    private lateinit var adapter: PlanTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanTimeMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSaveButton()
        observeAddTimeResponse()

        binding.planTimetableBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        adapter = PlanTimeAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.selectedTimeResults.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results.toList())
        }
    }

    private fun setupSaveButton() {
        binding.timeStoreButton.setOnClickListener {
            val currentDataList = adapter.getCurrentData()

            // 서버로 전송할 데이터 리스트를 기반으로 AddTimeRequest 객체를 생성합니다.
            val subjectDtoList = currentDataList.map {
                SubjectDtoList(
                    it.searchType,
                    it.searchName,
                    it.searchCredit
                )
            }
            val addTimeRequest = AddTimeRequest(
                SemesterDto(grade = 1, semester = 1),
                subjectDtoList
            ) // 예시값을 사용한 SemesterDto

            // ViewModel의 addTime 메서드를 호출하여 서버로 데이터를 전송합니다.
            viewModel.addTime(addTimeRequest)
        }
    }

    private fun observeAddTimeResponse() {
        viewModel.addTimeResponse.observe(viewLifecycleOwner) { response ->
            if (response != null && response.isSuccess) {
                Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                Log.d("PlanTimetableFragment", "Data saved successfully: ${response.message}")
            } else {
                Toast.makeText(context, "Failed to save data.", Toast.LENGTH_SHORT).show()
                Log.e("PlanTimetableFragment", "Failed to save data")
            }
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

