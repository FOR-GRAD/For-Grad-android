package umc.com.mobile.project.ui.plan

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.PointDtoList
import umc.com.mobile.project.data.model.career.Result
import umc.com.mobile.project.data.model.plan.TimeResult
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.databinding.ItemTimeSubjectBinding

class PlanRecyclerAdapter( timeList: List<TimeResult?>): RecyclerView.Adapter<PlanRecyclerAdapter.NonSubjectViewHolder>(){
    var timeList: List<TimeResult?> = timeList
        set(value) {
            field = value
            notifyDataSetChanged()
            //데이터가 바뀌었다 알려줌.
        }


    override fun getItemCount(): Int {
        return timeList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanRecyclerAdapter.NonSubjectViewHolder {
        val itemBinding = ItemTimeSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanRecyclerAdapter.NonSubjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlanRecyclerAdapter.NonSubjectViewHolder, position: Int) {

        holder.itemBinding.timeItemScore.text =timeList[position]?.searchCredit.toString()
        holder.itemBinding.timeItemSemester.text =timeList[position]?.searchGrade.toString()
        holder.itemBinding.timeItemSubject.text = timeList[position]?.searchName.toString()
    }

    class NonSubjectViewHolder(val itemBinding: ItemTimeSubjectBinding) : RecyclerView.ViewHolder(itemBinding.root)
}