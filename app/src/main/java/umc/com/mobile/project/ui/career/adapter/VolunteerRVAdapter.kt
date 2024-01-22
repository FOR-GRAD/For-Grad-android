package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.VolunteerDto

class VolunteerRVAdapter(private val volunteerList: ArrayList<VolunteerDto>): RecyclerView.Adapter<VolunteerRVAdapter.VolunteerViewHolder>(){

    override fun getItemCount(): Int {
        return volunteerList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolunteerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(volunteerList[position].date)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(volunteerList[position].title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(volunteerList[position].point)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(volunteerList[position].accumulatedPoint)
    }

    class VolunteerViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}