package umc.com.mobile.project.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.semesterResult
import umc.com.mobile.project.databinding.ItemChooseSemesterBinding

class PlanSemesterAdapter(semesterList: List<semesterResult?>, private val onItemClick: (semesterResult) -> Unit): RecyclerView.Adapter<PlanSemesterAdapter.NonSubjectViewHolder>() {
    var semesterList: List<semesterResult?> = semesterList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return semesterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanSemesterAdapter.NonSubjectViewHolder {
        val itemBinding = ItemChooseSemesterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NonSubjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlanSemesterAdapter.NonSubjectViewHolder, position: Int) {
        val semesterInfo = semesterList[position]
        if (semesterInfo != null) {
            holder.itemBinding.planTimeSemesterItem.text = semesterInfo.hakkiText
        }
        holder.itemBinding.root.setOnClickListener {
            if (semesterInfo != null) {
                onItemClick(semesterInfo)
            }
        }
    }


    class NonSubjectViewHolder(val itemBinding: ItemChooseSemesterBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
