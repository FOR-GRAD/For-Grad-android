package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.PlanTimeMainBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.plan.adapter.PlanTimeAdapter
import umc.com.mobile.project.ui.plan.viewmodel.PlanViewModel


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
        observeSelectedTimeResults()

        val spinner = binding.spinnerPlanTimeTrackSemester
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.semester_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "1학년 1학기" -> {
                        viewModel.setGradeAndSemester(1, 1)
                        viewModel.getTimeInfo(1, 1)
                        Log.d("selected Item",selectedItem)
                    }

                    "1학년 2학기" -> {
                        viewModel.setGradeAndSemester(1, 2)
                        viewModel.getTimeInfo(1, 2)
                    }

                    "2학년 1학기" -> {
                        viewModel.setGradeAndSemester(2, 1)
                        viewModel.getTimeInfo(2, 1)
                    }

                    "2학년 2학기" -> {
                        viewModel.setGradeAndSemester(2, 2)
                        viewModel.getTimeInfo(2, 2)
                    }

                    "3학년 1학기" -> {
                        viewModel.setGradeAndSemester(3, 1)
                        viewModel.getTimeInfo(3, 1)
                    }

                    "3학년 2학기" -> {
                        viewModel.setGradeAndSemester(3, 2)
                        viewModel.getTimeInfo(3, 2)
                    }

                    "4학년 1학기" -> {
                        viewModel.setGradeAndSemester(4, 1)
                        viewModel.getTimeInfo(4, 1)
                    }

                    "4학년 2학기" -> {
                        viewModel.setGradeAndSemester(4, 2)
                        viewModel.getTimeInfo(4, 2)
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 것도 선택되지 않은 경우의 동작 구현
            }
        }
        binding.timeStoreButton.setOnClickListener {
            val grade = viewModel.grade.value
            val semester = viewModel.semester.value

            if (grade == null || semester == null) {
                Toast.makeText(context, "학년과 학기를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendAddTimeRequest()
                viewModel.getTimeInfo(grade, semester)
            }
        }

        binding.titleMoveTimetable.setOnClickListener {
            navigate(R.id.action_planTimetableFragment_to_planSettingFragment)

        }

    }

    private fun observeSelectedTimeResults() {
        viewModel.selectedTimeResults.observe(viewLifecycleOwner) { selectedTimeResults ->
            (binding.recyclerView.adapter as PlanTimeAdapter).updateTimeList(selectedTimeResults)
        }
    }

    private fun setupRecyclerView() {
        adapter = PlanTimeAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}