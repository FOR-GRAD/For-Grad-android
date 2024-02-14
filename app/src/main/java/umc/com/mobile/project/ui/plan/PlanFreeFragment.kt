package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import umc.com.mobile.project.databinding.FragmentPlanFreeBinding
import umc.com.mobile.project.ui.plan.PlanViewModel

class PlanFreeFragment : Fragment() {
    private var _binding: FragmentPlanFreeBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanFreeBinding.inflate(inflater, container, false)

        viewModel.text.observe(viewLifecycleOwner) { text ->
            // Update your UI here with the LiveData update
            // Make sure 'textViewExample' matches the ID of the TextView in your layout
//            binding.textViewExample.text = text
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
