package umc.com.mobile.project.ui.gradInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentGradeBinding
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

		initSpinnerSchedule() // spinner 설정

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun initSpinnerSchedule() {
		binding.spinnerSchedule.onItemSelectedListener =
			object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(
					parent: AdapterView<*>?,
					view: View?,
					position: Int,
					id: Long
				) {
					if (!binding.spinnerSchedule.getItemAtPosition(position).equals("시간표 불러오기")) {
						Toast.makeText(
							context,
							"Selected: ${binding.spinnerSchedule.getItemAtPosition(position)}",
							Toast.LENGTH_LONG
						)
					}
				}

				override fun onNothingSelected(p0: AdapterView<*>?) {

				}
			}
	}
}