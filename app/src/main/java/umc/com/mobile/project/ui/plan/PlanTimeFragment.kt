package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import umc.com.mobile.project.databinding.FragmentPlanTimeBinding
import umc.com.mobile.project.ui.plan.PlanViewModel

class PlanTimeFragment : Fragment() {
    private var _binding: FragmentPlanTimeBinding? = null
    private val viewModel: PlanViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanTimeBinding.inflate(inflater, container, false)

        // API 호출을 처리하는 함수 호출
//        handleApiCall()

        viewModel.text.observe(viewLifecycleOwner) { text ->
            // Update your UI here with the LiveData update
            // Make sure 'textViewExample' matches the ID of the TextView in your layout
            // binding.textViewExample.text = text
        }
        return binding.root
    }

//    private fun handleApiCall() {
//        // Retrofit을 사용하여 API 호출
//        val service = RetrofitClient.createService(CertificationService::class.java)
//
//        // 실제 API 요청을 여기서 수행합니다.
//        // 예를 들어, 특정 자격증 정보를 조회하고자 한다면, CertificationService의 함수를 호출하여 API 요청을 수행합니다.
//        // 아래는 예시 코드입니다.
//        val certificationCode = "1320" // 자격증 코드
//        val apiKey = "YOUR_API_KEY" // 실제 API 키로 대체
//
//        service.getCertificationInfo(apiKey, certificationCode).enqueue(object : Callback<YourResponseType> {
//            override fun onResponse(call: Call<YourResponseType>, response: Response<YourResponseType>) {
//                if (response.isSuccessful) {
//                    // API 호출이 성공한 경우
//                    val responseData = response.body()
//                    // ViewModel의 LiveData를 통해 UI에 데이터 업데이트
//                    viewModel.text.value = responseData?.toString()
//                } else {
//                    // API 호출이 실패한 경우
//                    // 에러 처리 로직을 추가하세요.
//                }
//            }
//
//            override fun onFailure(call: Call<YourResponseType>, t: Throwable) {
//                // 네트워크 에러 처리
//                t.printStackTrace()
//            }
//        })
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
