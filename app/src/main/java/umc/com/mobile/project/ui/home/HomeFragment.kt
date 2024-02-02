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
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.board.viewmodel.GradDateViewModel
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
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

		navigateFragment() // 페이지 이동
		saveCheeringMemo() // 응원의 한마디 연결
		viewModel.getUserInfo() // 홈 화면 정보 조회 api

		viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
			binding.tvName.text = it?.result?.name
			binding.tvStdId.text = it?.result?.id.toString()
			binding.tvSchool.text = it?.result?.department
			binding.tvGrade.text = it?.result?.grade
			binding.tvStatus.text = it?.result?.status
			binding.tvCheeringWord.text = it?.result?.message

			val decodedBytes: ByteArray = Base64.decode(it?.result?.base64Image, Base64.DEFAULT)
			val decodedImage = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
			binding.ivHomeProfile.setImageBitmap(decodedImage)
		})

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

	private fun navigateFragment() {
		binding.btnCheeringWordMove.setOnClickListener {
			navigate(R.id.action_fragment_home_to_fragment_date)
		}
	}

	private fun saveCheeringMemo() {
		gradDateViewModel.cheeringMemo.observe(viewLifecycleOwner, Observer {
			Log.d("LiveData", "CheeringMemo updated: $it")
			binding.tvCheeringWord.text = it
		})
	}
}