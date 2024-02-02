package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.home.TimeTableDto
import umc.com.mobile.project.databinding.FragmentGradeBinding
import umc.com.mobile.project.ui.gradInfo.adapter.GradeRVAdapter
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel

class GradeFragment : Fragment() {
	private var _binding: FragmentGradeBinding? = null
	private val viewModel: GradInfoViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGradeBinding.inflate(inflater, container, false)

		viewModel.getGradRequirementsInfo() // 사용자 졸업 요건 조회 api
		initRecyclerView() // 성적 사항 recycleView 연결

		viewModel.gradesInfo.observe(viewLifecycleOwner, Observer {
			// 임시 코드
			binding.tvAppliedCredit.text = it?.result?.semesters?.get("2022 학년도 1학기")?.gradesTotalDto?.appliedCredits
			binding.tvAcquiredCredit.text = it?.result?.semesters?.get("1학기")?.gradesTotalDto?.acquiredCredits
			binding.tvAverageTotal.text = it?.result?.semesters?.get("1학기")?.gradesTotalDto?.totalGrade
			binding.tvAverageGrade.text = it?.result?.semesters?.get("1학기")?.gradesTotalDto?.averageGrade
			binding.tvPercent.text = it?.result?.semesters?.get("1학기")?.gradesTotalDto?.percentile

			Log.d("gradsInfo", "${binding.tvPercent}")
		})

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun initRecyclerView() {
		val adapter = GradeRVAdapter()

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager =
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) //레이아웃 매니저 연결
	}
}