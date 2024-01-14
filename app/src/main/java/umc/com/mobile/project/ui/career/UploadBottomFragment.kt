package umc.com.mobile.project.ui.career

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentUploadBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerEditViewModel

class UploadBottomFragment (context: Context) : BottomSheetDialogFragment() {
    private var _binding: FragmentUploadBottomBinding? = null
    private val viewModel: CareerEditViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBottomBinding.inflate(inflater, container, false)

        /*viewModel.text.observe(viewLifecycleOwner) {
            binding.textCareer.text = it
        }*/
        _binding!!.ivUploadBottomClose.setOnClickListener {
            dialog!!.dismiss()
            dialog!!.cancel()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}