package umc.com.mobile.project.ui.plan

import android.content.res.ColorStateList
import android.os.Bundle
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

class PlanlicenseFragment : Fragment() {
    private var _binding: FragmentPlanlicenseBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlanlicenseBinding.inflate(inflater, container, false)

        setupAddRowButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isFilledAllOptions.observe(viewLifecycleOwner, Observer { isFilled ->
            // 여기에서 `isFilled` 값에 따라 UI를 업데이트합니다.
            val colorResId = if (isFilled) R.color.skyBlue else R.color.gray
            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.licenseButtonStore.backgroundTintList = ColorStateList.valueOf(color)
        })
    }

    private fun setupAddRowButton() {
        binding.licenseAddrowButton.setOnClickListener {
            addRowToGridLayout()
        }
    }

    private fun addRowToGridLayout() {
        val gridLayout = binding.myGridLayout // GridLayout 인스턴스를 가져옵니다.
        val columnCount = 2 // GridLayout의 열 수를 직접 설정합니다.

        // 새로운 행에 들어갈 EditText 두 개를 생성하고 설정합니다.
        for (i in 0 until columnCount) {
            val newEditText = EditText(context).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0 // GridLayout에서 가중치를 사용할 때는 0dp로 설정합니다.
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(10) // 모든 마진 설정
                }
                gravity = View.TEXT_ALIGNMENT_CENTER
                setPadding(20, 20, 20, 20)
                background = ContextCompat.getDrawable(requireContext(), R.drawable.edittext_white_line) // 배경 설정
            }

            // GridLayout에 새로운 EditText 추가
            gridLayout.addView(newEditText)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
