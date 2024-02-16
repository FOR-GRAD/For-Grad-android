package umc.com.mobile.project.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import umc.com.mobile.project.data.model.gradInfo.GradesTotalDto
import umc.com.mobile.project.data.model.home.TimeTableDto
import umc.com.mobile.project.databinding.ItemHomeNextPlanBinding

class NextPlanRVAdapter : RecyclerView.Adapter<NextPlanRVAdapter.MyViewHolder>() {
	private var dataList = mutableListOf<TimeTableDto>()

	inner class MyViewHolder(private val binding: ItemHomeNextPlanBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(timeTableDto: TimeTableDto) {
			binding.tvKindTitle.text = timeTableDto.majorType
			binding.tvClassTitle.text = timeTableDto.subject
			binding.tvGradeTitle.text = timeTableDto.grades.toString()
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val binding = ItemHomeNextPlanBinding.inflate(
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

	fun setData(data: List<TimeTableDto>) {
		dataList.clear()
		dataList.addAll(data)
		notifyDataSetChanged()
	}

}
