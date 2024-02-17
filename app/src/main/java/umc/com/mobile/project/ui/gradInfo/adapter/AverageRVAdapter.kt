package umc.com.mobile.project.ui.gradInfo.adapter

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import umc.com.mobile.project.data.model.gradInfo.GradesTotalDto
import umc.com.mobile.project.databinding.ItemAverageGradeBinding
import umc.com.mobile.project.ui.gradInfo.GradeFragment
import umc.com.mobile.project.ui.gradInfo.viewmodel.GradeViewModel

class AverageRVAdapter(private val viewModel: GradeViewModel) :
	RecyclerView.Adapter<AverageRVAdapter.MyViewHolder>() {
	private var dataList = mutableListOf<GradesTotalDto>()
	var totalAverageGrade = 0.0

	inner class MyViewHolder(private val binding: ItemAverageGradeBinding) :
		RecyclerView.ViewHolder(binding.root) {
		init {
			binding.root.setOnClickListener {
				val position = adapterPosition
				if (position != RecyclerView.NO_POSITION) {
					val semester = "${position + 1} 학기"
					val semesterGrade = "${position + 1} 학기 성적"
					viewModel.onSemesterItemClick(semester)
					viewModel.onSemesterGradeItemClick(semesterGrade)

					if (dataList[position].averageGrade == "0.0") {
						viewModel.onSetNullCheckGrade(true)
					} else {
						viewModel.onSetNullCheckGrade(false)
					}
				}
			}
		}

		fun bind(gradesTotalDto: GradesTotalDto, position: Int) {
			val semester = if (position % 2 == 0) {
				1
			} else {
				2
			}
			val grade = if (position % 2 == 0) position / 2 + 1 else (position + 1) / 2
//			totalAverageGrade += Integer.parseInt(gradesTotalDto.averageGrade)

			binding.tvSemesterContent1.text = "$grade - $semester"
			binding.tvAverageGradeContent1.text = gradesTotalDto.averageGrade
//			viewModel.onSetTotalAverageGrade(totalAverageGrade, position+1)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val binding = ItemAverageGradeBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MyViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.bind(dataList[position], position)
	}

	override fun getItemCount(): Int {
		return dataList.size
	}

	fun setData(data: List<GradesTotalDto>) {
		dataList.clear()
		dataList.addAll(data)
		repeat(8 - dataList.size) {
			dataList.add(
				GradesTotalDto(
					"0",
					"0",
					"0.0",
					"0.0",
					"0"
				)
			)
		}
		notifyDataSetChanged()
	}



}
