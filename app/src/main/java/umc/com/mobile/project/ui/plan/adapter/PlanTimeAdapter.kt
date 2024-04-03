package umc.com.mobile.project.ui.plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.timetable.UpTimeResult
import umc.com.mobile.project.databinding.ItemPlanTimeBinding
import umc.com.mobile.project.ui.plan.viewmodel.PlanViewModel

class PlanTimeAdapter(private val viewModel: PlanViewModel) :
	ListAdapter<UpTimeResult, PlanTimeAdapter.ViewHolder>(
		UpTimeResultDiffCallback()
	) {
	private var onItemLongClickListener: ((UpTimeResult) -> Unit)? = null
	private var onItemClickListener: ((UpTimeResult) -> Unit)? = null

	class ViewHolder(private val binding: ItemPlanTimeBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: UpTimeResult) {
			binding.timeKindTitle.text = item.type
			binding.timeClassTitle.text = item.name
			binding.timeGradeTitle.text = item.credit.toString()
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding =
			ItemPlanTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item)

		holder.itemView.setOnLongClickListener {
			onItemLongClickListener?.invoke(item)
			val position = holder.adapterPosition
			if (position != RecyclerView.NO_POSITION) {
				val clickedItem = getItem(position)
				viewModel.subjectId.value?.let { it1 ->
					viewModel.deleteTimeTable(
						viewModel.grade.value!!,
						viewModel.semester.value!!,
						it1
					)
				}
			}
			true
		}

		holder.itemView.setOnClickListener {
			onItemClickListener?.invoke(item)
		}
	}

	fun updateTimeList(addnewtime: ArrayList<UpTimeResult>) {
		submitList(addnewtime)
		notifyDataSetChanged()
	}

	/*
	fun setOnItemLongClickListener(listener: (UpTimeResult) -> Unit) {
		this.onItemLongClickListener = listener
	}

	fun setOnItemClickListener(listener: (UpTimeResult) -> Unit) {
		this.onItemClickListener = listener
	}
	*/

	class UpTimeResultDiffCallback : DiffUtil.ItemCallback<UpTimeResult>() {
		override fun areItemsTheSame(
			oldItem: UpTimeResult,
			newItem: UpTimeResult
		): Boolean {
			// subjectId를 비교하여 동일한 아이템인지 판별
			return oldItem.name == newItem.name
		}

		override fun areContentsTheSame(
			oldItem: UpTimeResult,
			newItem: UpTimeResult
		): Boolean {
			// 내용이 동일한지 비교
			return oldItem == newItem
		}
	}
}
