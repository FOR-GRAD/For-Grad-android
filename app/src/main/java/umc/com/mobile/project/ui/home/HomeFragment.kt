package umc.com.mobile.project.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentHomeBinding
import umc.com.mobile.project.ui.board.GradDateFragment
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
	private var _binding: FragmentHomeBinding? = null
	private val viewModel: HomeViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		binding.btnCheeringWordMove.setOnClickListener {
			navigate(R.id.action_fragment_home_to_fragment_date)
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}