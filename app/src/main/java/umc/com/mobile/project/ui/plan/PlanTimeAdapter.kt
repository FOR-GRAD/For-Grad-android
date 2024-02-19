import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.TimeResult
import umc.com.mobile.project.databinding.ItemPlanTimeBinding

class PlanTimeAdapter : ListAdapter<TimeResult, PlanTimeAdapter.ViewHolder>(TimeResultDiffCallback()) {

    // ViewHolder 클래스는 그대로 유지합니다.
    class ViewHolder(private val binding: ItemPlanTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimeResult) {
            binding.timeKindTitle.text = item.searchType
            binding.timeClassTitle.text = item.searchName
            binding.timeGradeTitle.text = item.searchCredit.toString() // toString() 추가, 학점을 문자열로 표시
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

    // 현재 데이터 리스트를 가져오는 편의 메서드를 추가합니다.
    fun getCurrentData(): List<TimeResult> {
        return currentList
    }

    class TimeResultDiffCallback : DiffUtil.ItemCallback<TimeResult>() {
        override fun areItemsTheSame(oldItem: TimeResult, newItem: TimeResult): Boolean {
            return oldItem.searchName == newItem.searchName
        }

        override fun areContentsTheSame(oldItem: TimeResult, newItem: TimeResult): Boolean {
            return oldItem == newItem
        }
    }
}
