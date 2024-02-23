package umc.com.mobile.project.ui.plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.TimeInfoResponse
import umc.com.mobile.project.databinding.ItemPlanTimeBinding

class PlanTimeAdapter(private var addnewtime: List<Any> = ArrayList()): ListAdapter<TimeInfoResponse, PlanTimeAdapter.ViewHolder>(
	UpTimeResultDiffCallback()
) {

    class ViewHolder(private val binding: ItemPlanTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimeInfoResponse) {
            binding.timeKindTitle.text = item.type
            binding.timeClassTitle.text = item.name
            // 학점을 문자열로 변환하여 표시
            binding.timeGradeTitle.text = item.credit.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun updateTimeList(addnewtime: ArrayList<TimeInfoResponse>) {
        submitList(addnewtime)
	    notifyDataSetChanged()
    }

    class UpTimeResultDiffCallback : DiffUtil.ItemCallback<TimeInfoResponse>() {
        override fun areItemsTheSame(oldItem: TimeInfoResponse, newItem: TimeInfoResponse): Boolean {
            // subjectId를 비교하여 동일한 아이템인지 판별
            return oldItem.name== newItem.name
        }

        override fun areContentsTheSame(oldItem: TimeInfoResponse, newItem: TimeInfoResponse): Boolean {
            // 내용이 동일한지 비교
            return oldItem == newItem
        }
    }
}
