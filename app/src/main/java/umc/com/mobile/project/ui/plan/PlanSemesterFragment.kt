package umc.com.mobile.project.ui.plan

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.gridlayout.widget.GridLayout
import umc.com.mobile.project.R
import umc.com.mobile.project.databinding.FragmentPlanlicenseBinding
import android.widget.EditText
import androidx.core.view.setMargins
import androidx.lifecycle.Observer
import umc.com.mobile.project.data.model.plan.SaveInfo
import umc.com.mobile.project.data.model.plan.SavelicenseRequest
import umc.com.mobile.project.data.model.plan.semesterResult
import umc.com.mobile.project.databinding.SemesterChooseBinding
import java.time.LocalDate

class PlanSemesterFragment : Fragment() {
	private var _binding: SemesterChooseBinding? = null
	private val viewModel: PlanViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = SemesterChooseBinding.inflate(inflater, container, false)





		return binding.root
	}



	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}


