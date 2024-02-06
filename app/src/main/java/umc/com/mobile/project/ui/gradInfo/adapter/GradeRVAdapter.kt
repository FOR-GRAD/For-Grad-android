package umc.com.mobile.project.ui.gradInfo.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.databinding.ItemClassAndGradeBinding
class GradeRVAdapter : RecyclerView.Adapter<GradeRVAdapter.MyViewHolder>() {

	private var dataList = mutableListOf<GradesResponse>()

	inner class MyViewHolder(private val binding: ItemClassAndGradeBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(gradesResponse: GradesResponse) {
			binding.tvKindTitle.text = gradesResponse.result.semesters["1학기"]?.gradesDtoList?.get(0)?.classification
			binding.tvClassTitle.text = gradesResponse.result.semesters["학기"]?.gradesDtoList?.get(1)?.subjectName
			binding.tvCreditTitle.text = gradesResponse.result.semesters["학기"]?.gradesDtoList?.get(2)?.credits
			binding.tvGradeTitle.text = gradesResponse.result.semesters["학기"]?.gradesDtoList?.get(3)?.grade
			binding.tvCurrentTrackTitle.text = gradesResponse.result.semesters["학기"]?.gradesDtoList?.get(4)?.track
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val binding = ItemClassAndGradeBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MyViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.bind(dataList[position])
	}

	override fun getItemCount(): Int {
		return dataList.size
	}

}
