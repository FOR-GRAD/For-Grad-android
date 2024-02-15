package umc.com.mobile.project.ui.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.semesterResult
import umc.com.mobile.project.databinding.ItemChooseSemesterBinding

class PlanSemesterAdapter( semesterList: List<semesterResult?>): RecyclerView.Adapter<PlanSemesterAdapter.NonSubjectViewHolder>(){
	var semesterList: List<semesterResult?> = semesterList
		set(value) {
			field = value
			notifyDataSetChanged()
			//데이터가 바뀌었다 알려줌.
		}


	override fun getItemCount(): Int {
		return semesterList?.size ?: 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanSemesterAdapter.NonSubjectViewHolder {
		val itemBinding = ItemChooseSemesterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PlanSemesterAdapter.NonSubjectViewHolder(itemBinding)
	}

	override fun onBindViewHolder(holder: PlanSemesterAdapter.NonSubjectViewHolder, position: Int) {

		holder.itemBinding.planTimeSemesterItem.text =semesterList[position]?.hakkiText.toString()

	}

	class NonSubjectViewHolder(val itemBinding: ItemChooseSemesterBinding) : RecyclerView.ViewHolder(itemBinding.root)
}