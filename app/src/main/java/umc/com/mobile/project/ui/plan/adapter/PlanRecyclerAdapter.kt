package umc.com.mobile.project.ui.plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.timetable.TimeResult
import umc.com.mobile.project.databinding.ItemTimeSubjectBinding

class PlanRecyclerAdapter(
	private var timeList: List<TimeResult?>,
	private val onAddButtonClicked: ((TimeResult?) -> Unit)? = null
) : RecyclerView.Adapter<PlanRecyclerAdapter.NonSubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonSubjectViewHolder {
        val itemBinding = ItemTimeSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NonSubjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NonSubjectViewHolder, position: Int) {
        val timeResult = timeList[position]
        holder.bind(timeResult, onAddButtonClicked)
    }

    override fun getItemCount(): Int = timeList.size

    fun updateTimeList(newTimeList: List<TimeResult>) {
        this.timeList = newTimeList
        notifyDataSetChanged() // 데이터가 변경되었음을 알리고 UI를 갱신
    }

    class NonSubjectViewHolder(private val itemBinding: ItemTimeSubjectBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(timeResult: TimeResult?, onAddButtonClicked: ((TimeResult?) -> Unit)?) {
            itemBinding.timeItemSubject.text=timeResult?.searchName.toString()
            itemBinding.timeItemSemester.text = timeResult?.searchGrade.toString()
            val divisionAndCredit = "${timeResult?.searchType} / ${timeResult?.searchCredit}"
            itemBinding.timeItemScore.text = divisionAndCredit

            // 버튼 클릭 이벤트 설정
            itemBinding.planTimeAddButton.setOnClickListener {
                onAddButtonClicked?.invoke(timeResult)
            }
        }
    }
}
