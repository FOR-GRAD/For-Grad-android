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
import okhttp3.internal.notify
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.home.TimeTableDto
import umc.com.mobile.project.databinding.FragmentGradeBinding
import umc.com.mobile.project.ui.gradInfo.adapter.GradeRVAdapter
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradInfoViewModel
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

		// 성적 정보 갱신 관찰
//		viewModel.gradesInfo.observe(viewLifecycleOwner, Observer {
//			//
//		})

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
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
	}
}