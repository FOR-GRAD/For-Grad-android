package umc.com.mobile.project.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.home.adapter.NextPlanRVAdapter
import umc.com.mobile.project.ui.home.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
	private var _binding: FragmentHomeBinding? = null
	private val viewModel: HomeViewModel by viewModels()
	private val gradDateViewModel: GradDateViewModel by activityViewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		binding.lifecycleOwner = viewLifecycleOwner
		binding.vm = viewModel

		viewModel.getUserInfo() // 홈 화면 정보 조회 api

		navigateFragment()
		saveCheeringMemo() // 응원의 한마디 연결
		setupRecyclerView() // recyclerView 연결
		setupHomeInfoRetrofit() // 홈 화면 ui 연결

		return binding.root
	}

	override fun onPause() {
		super.onPause()
		saveCheeringMemo()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	// 페이지 이동
	private fun navigateFragment() {
		binding.btnCheeringWordMove.setOnClickListener {
			navigate(R.id.action_fragment_home_to_fragment_date)
		}
	}

	private fun saveCheeringMemo() {
		gradDateViewModel.dateResponse.observe(viewLifecycleOwner, Observer {
			binding.tvCheeringWord.text = it?.result?.message
		})
	}

	private fun setupRecyclerView() {
		val adapter = NextPlanRVAdapter()

		viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer { userInfoResponse ->
			val futureTimeTableDto = userInfoResponse?.result?.futureTimeTableDto
			if (futureTimeTableDto != null) {
				val futureSemesterInfo = futureTimeTableDto.values.firstOrNull()
				if (futureSemesterInfo?.semester != null) {
					val timeTableDtoList = futureSemesterInfo.semester.timeTableDtoList
					adapter.setData(timeTableDtoList)
				} else {
					Log.e("HomeFragment", "No future semester information available")
				}
			} else {
				Log.e("HomeFragment", "No future time table information available")
			}
		})

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager =
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
	}

	private fun setupHomeInfoRetrofit() {
		viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
			/**
			 * 기본 정보
			 */
			binding.tvName.text = it?.result?.name
			binding.tvStdId.text = it?.result?.id.toString()
			binding.tvSchool.text = it?.result?.department
			binding.tvGrade.text = it?.result?.grade
			binding.tvStatus.text = it?.result?.status
			binding.tvGraduateDday.text = it?.result?.dday.toString()
			binding.tvCheeringWord.text = it?.result?.message

			val decodedBytes: ByteArray = Base64.decode(it?.result?.base64Image, Base64.DEFAULT)
			val decodedImage = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
			binding.ivHomeProfile.setImageBitmap(decodedImage)


			/**
			 * 나만의 계획
			 */
			binding.tvGradeSemester.text =
				it?.result?.futureTimeTableDto?.keys?.joinToString(separator = ", ")

			binding.tvTotalCredit.text =
				(it?.result?.futureTimeTableDto?.get(it.result.futureTimeTableDto.keys.joinToString(separator = ", "))?.semester?.sumCredits ?: "총 학점 0").toString()

			val dDay = it?.result?.dday ?: 0
//			binding.progressbarToGrad.progress = 1460 - dDay
			binding.progressbarToGrad.progress = 1095 // 임의값
		})
	}
}