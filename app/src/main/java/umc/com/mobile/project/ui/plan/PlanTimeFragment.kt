package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.project.R
import umc.com.mobile.project.data.model.plan.TimeResult
import umc.com.mobile.project.databinding.FragmentPlanTimeBinding
import umc.com.mobile.project.databinding.PlanSubjectListBinding
import umc.com.mobile.project.ui.common.NavigationUtil.navigate
import umc.com.mobile.project.ui.plan.PlanViewModel

class PlanTimeFragment : Fragment() {
	private var _binding: PlanSubjectListBinding? = null
	private val viewModel: PlanViewModel by viewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = PlanSubjectListBinding.inflate(inflater, container, false)



		viewModel.getListTimeInfo()
		val adapter = viewModel.listTimeInfo.value?.let { PlanRecyclerAdapter(it.result) }

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager =
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)



		viewModel.listTimeInfo.observe(viewLifecycleOwner) { timenewList ->
			if (timenewList != null) {
				if (adapter != null) {
					adapter.timeList=timenewList.result
					adapter.notifyDataSetChanged()
				}
			}




		}

		_binding!!.planSubjectListSemester.setOnClickListener {
			//api 연결
//            viewModel.addCareer()
			navigate(R.id.action_planSettingFragment_to_planSemesterFragment)
		}



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
