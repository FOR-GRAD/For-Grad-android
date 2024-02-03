package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemCertificateBinding

class VolunteerRVAdapter(
    private val volunteerList: List<ActivityWithAccumulatedHour>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VolunteerRVAdapter.VolunteerViewHolder>(){

    override fun getItemCount(): Int {
        return volunteerList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolunteerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = volunteerList[position].startDate ?: ""
        val title = volunteerList[position].title ?: ""
        val volunteerHour = volunteerList[position].volunteerHour?.toString() ?: ""
        val accumulatedHour = volunteerList[position].accum.toString() ?: ""

        val volunteerHourWithUnit = "$volunteerHour h"
        val accumulatedHourWithUnit = "$accumulatedHour h"

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(volunteerHourWithUnit)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(accumulatedHourWithUnit)
    }

    class VolunteerViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
interface OnItemClickListener {
    fun onItemClick(itemId: Long)
}