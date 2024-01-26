package umc.com.mobile.project.ui.home

import android.os.Bundle
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

		navigateFragment() // 페이지 이동
		saveCheeringMemo() // 응원의 한마디 연결
		viewModel.getUserInfo() // 홈 화면 정보 조회 api

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

		binding.btnNavigateGradInfo.setOnClickListener {
			navigate(R.id.action_fragment_home_to_fragment_grad_info)
		}
	}

	private fun saveCheeringMemo() {
		gradDateViewModel.cheeringMemo.observe(viewLifecycleOwner, Observer {
			Log.d("LiveData", "CheeringMemo updated: $it")
			binding.tvCheeringWord.text = it
		})
	}
}