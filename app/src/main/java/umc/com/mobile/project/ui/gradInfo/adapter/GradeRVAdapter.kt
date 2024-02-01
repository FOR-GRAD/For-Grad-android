package umc.com.mobile.project.ui.gradInfo.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.Gson
import umc.com.mobile.project.data.model.gradInfo.GradesResponse
import umc.com.mobile.project.data.model.gradInfo.SemesterGradesDto
import umc.com.mobile.project.databinding.ItemClassAndGradeBinding
class GradeRVAdapter : RecyclerView.Adapter<GradeRVAdapter.MyViewHolder>() {

	private var dataList = mutableListOf<SemesterGradesDto>()

	inner class MyViewHolder(private val binding: ItemClassAndGradeBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(semesterGradesDto: SemesterGradesDto) {
			// 여기서 ViewHolder에 데이터를 바인딩하도록 구현
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
