package umc.com.mobile.project.ui.gradInfo.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import umc.com.mobile.project.data.model.gradInfo.GradesDto
import umc.com.mobile.project.databinding.ItemClassAndGradeBinding
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradeViewModel

class GradeRVAdapter(private val viewModel: GradeViewModel) :
	RecyclerView.Adapter<GradeRVAdapter.MyViewHolder>() {
	private var dataList = mutableListOf<GradesDto>()
	private var selectedSemester = ""

	inner class MyViewHolder(private val binding: ItemClassAndGradeBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(gradesDto: GradesDto) {
			binding.tvKindTitle.text = gradesDto.subjectName
			binding.tvClassTitle.text = gradesDto.classification
			binding.tvCreditTitle.text = gradesDto.credits
			binding.tvGradeTitle.text = gradesDto.grade
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

	fun updateSelectedSemester(selectedSemester: String) {
		this.selectedSemester = selectedSemester

		val semestersInfo = viewModel.semesters?.value?.get(selectedSemester)

		semestersInfo?.let {
			dataList.clear()
			dataList.addAll(it)
			notifyDataSetChanged()
		} ?: run {
			dataList.clear()
			notifyDataSetChanged()
		}
	}
}
