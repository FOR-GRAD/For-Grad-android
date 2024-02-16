package umc.com.mobile.project.ui.gradInfo

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.internal.notify
import umc.com.mobile.project.R
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.databinding.FragmentGradeBinding
import umc.com.mobile.project.ui.gradInfo.adapter.AverageRVAdapter
import umc.com.mobile.project.ui.gradInfo.adapter.GradeRVAdapter
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradeViewModel

class GradeFragment : Fragment() {
	private var _binding: FragmentGradeBinding? = null
	private val viewModel: GradeViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradeBinding.inflate(inflater, container, false)

		viewModel.getGradeInfo() // 사용자 성적 사항 조회 api
		initRecyclerView() // 성적 사항 recycleView 연결

		viewModel.selectedSemester.observe(viewLifecycleOwner, Observer { selectedSemester ->
			(binding.recyclerView.adapter as? GradeRVAdapter)?.updateSelectedSemester(
				selectedSemester
			)
			binding.tvSemester.text = selectedSemester
		})

		viewModel.totalAverage.observe(viewLifecycleOwner, Observer { totalAverageGrade ->
			binding.tvAverageTotal.text = totalAverageGrade.toString()
		})

		viewModel.selectedSemesterGradeAndGrades.observe(viewLifecycleOwner) { pair ->
			val selectedGrade = pair.first
			val gradesMap = pair.second

			Log.d("selectedGrade", "$selectedGrade")
			Log.d("gradesMap", "$gradesMap")

			val selectedGradeInfo = gradesMap?.get("$selectedGrade")
			Log.d("selectedGradeInfo", "$selectedGradeInfo")

			binding.tvAcquiredCredit.text = selectedGradeInfo?.acquiredCredits
			binding.tvAppliedCredit.text = selectedGradeInfo?.appliedCredits
			binding.tvAverageGrade.text = selectedGradeInfo?.averageGrade
			binding.tvAverageTotal.text = selectedGradeInfo?.totalGrade
			binding.tvPercent.text = selectedGradeInfo?.percentile
		}

		viewModel.grades.observe(viewLifecycleOwner, Observer { gradesMap ->
			(binding.recyclerView2.adapter as? AverageRVAdapter)?.setData(gradesMap.values.toList())
		})

		viewModel.isNullCheckGrade.observe(viewLifecycleOwner) {
			if (it) {	showDialog()	}
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun initRecyclerView() {
		val adapter = GradeRVAdapter(viewModel)
		val adapter2 = AverageRVAdapter(viewModel)

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = LinearLayoutManager(context)

		binding.recyclerView2.adapter = adapter2
		binding.recyclerView2.layoutManager = GridLayoutManager(context, 2)
	}

	private fun showDialog() {
		val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogTheme)

		val customView = layoutInflater.inflate(R.layout.custom_dialog_grade, null)
		dialogBuilder.setView(customView)

		val alertDialog = dialogBuilder.create()
		alertDialog.show()
	}
}
