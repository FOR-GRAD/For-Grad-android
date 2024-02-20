package umc.com.mobile.project.ui.plan

import android.content.res.ColorStateList
import umc.com.mobile.project.data.model.plan.SaveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.com.mobile.project.R
import umc.com.mobile.project.data.model.plan.CertificateLicenseRequest
import umc.com.mobile.project.databinding.FragmentPlanlicenseBinding

class PlanlicenseFragment : Fragment() {
    private var _binding: FragmentPlanlicenseBinding? = null
    private val viewModel: PlanViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var edit:Boolean=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanlicenseBinding.inflate(inflater, container, false)
        binding.licenseCertificateButton.setOnClickListener {
            if(edit){
                var licenseList=ArrayList<CertificateLicenseRequest>() // 빈 배열만듦
                viewModel.licenseInfo.value?.result?.get(0)?.certificateId?.let { it1 ->
                    CertificateLicenseRequest(
                        it1,
                        binding.planLicenseName.text.toString(),binding.planLicenseDate.text.toString())
                }?.let { it2 -> licenseList.add(it2) }

                viewModel.licenseInfo.value?.result?.get(1)?.certificateId?.let { it1 ->
                    CertificateLicenseRequest(
                        it1,
                        binding.planLicenseName2.text.toString(),binding.planLicenseDate2.text.toString())
                }?.let { it2 -> licenseList.add(it2) }


                viewModel.licenseInfo.value?.result?.get(2)?.certificateId?.let { it1 ->
                    CertificateLicenseRequest(
                        it1,
                        binding.planLicenseName3.text.toString(),binding.planLicenseDate3.text.toString())
                }?.let { it2 -> licenseList.add(it2) }

                viewModel.licenseInfo.value?.result?.get(3)?.certificateId?.let { it1 ->
                    CertificateLicenseRequest(
                        it1,
                        binding.planLicenseName4.text.toString(),binding.planLicenseDate4.text.toString())
                }?.let { it2 -> licenseList.add(it2) }





                viewModel.certificateLicense(licenseList)


            }
        }



        viewModel.getLicenseInfo() // api 연결




        // UPlicenseResponse-자격증 정보 불러오기
        viewModel.licenseInfo.observe(viewLifecycleOwner) { licenseInfo ->
            licenseInfo?.result?.let { resultList ->
                if (resultList.isNotEmpty()) {
                    resultList.forEachIndexed { index, result ->
                        when (index) {
                            0 -> {
                                binding.planLicenseName.setText(result.name)
                                binding.planLicenseDate.setText(result.date)

                            }
                            1 -> {
                                binding.planLicenseName2.setText(result.name)
                                binding.planLicenseDate2.setText(result.date)

                            }
                            2 -> {
                                binding.planLicenseName3.setText(result.name)
                                binding.planLicenseDate3.setText(result.date)

                            }
                            3 -> {
                                binding.planLicenseName4.setText(result.name)
                                binding.planLicenseDate4.setText(result.date)

                            }
                        }
                    }
                    setupEditTextListeners()
                }
            }
        }

        return binding.root
    }


    private fun submitData() {

        val name = binding.planLicenseName.text.toString()
        val date = binding.planLicenseDate.text.toString()

        // SaveInfo 객체 리스트 생성
        val saveInfoList = listOf(SaveInfo(name, date))

        // SavelicenseRequest 객체 생성


        // API 호출
        viewModel.saveLicense(saveInfoList)

    }



    private fun setupEditTextListeners() {

        binding.planLicenseName.addTextChangedListener(createTextWatcher())
        binding.planLicenseName2.addTextChangedListener(createTextWatcher())
        binding.planLicenseName3.addTextChangedListener(createTextWatcher())
        binding.planLicenseName4.addTextChangedListener(createTextWatcher())
    }
    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edit=true

            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }
    private fun checkIfAnyInputIsFilled() {
        val isAnyFieldFilled = binding.planLicenseName.text.trim().isNotEmpty()
        //버튼 활성화 업데이트
        binding.licenseButtonStore.isEnabled = isAnyFieldFilled

        // 버튼 색상도 업데이트
        val colorResId = if (isAnyFieldFilled) R.color.skyBlue else R.color.gray
        val color = ContextCompat.getColor(requireContext(), colorResId)
        binding.licenseButtonStore.backgroundTintList = ColorStateList.valueOf(color)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditTextListeners()
        checkIfAnyInputIsFilled() // 초기 상태 확인
        binding.licenseButtonStore.setOnClickListener {
            submitData() // 사용자 입력을 기반으로 API 호출
        }
        binding.licenseDeleteButton.setOnClickListener {
            deleteLicense() // 삭제 API 호출
        }











    }
    private fun deleteLicense() {
        // 삭제할 자격증 ID
        val certificateId = 1 // 예시로 1을 사용하였습니다. 실제로는 삭제할 자격증의 ID를 전달해야 합니다.

        // API 호출
//        viewModel.deleteLicense(certificateId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}