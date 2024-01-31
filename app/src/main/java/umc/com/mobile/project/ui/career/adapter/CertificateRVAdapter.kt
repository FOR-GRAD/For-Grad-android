package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemCertificateBinding

class CertificateRVAdapter(private val certificateList: List<ActivityWithAccumulatedHour>): RecyclerView.Adapter<CertificateRVAdapter.VolunteerViewHolder>(){

    override fun getItemCount(): Int {
        return certificateList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolunteerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = certificateList[position].startDate ?: ""
        val title = certificateList[position].title ?: ""
        val certificationType = certificateList[position].certificationType?.toString() ?: ""
        val accum = certificateList[position].accum.toString() ?: ""

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(certificationType)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(accum)
    }

    class VolunteerViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}