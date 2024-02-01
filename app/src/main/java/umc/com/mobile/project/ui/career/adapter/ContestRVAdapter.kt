package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemCertificateBinding

class ContestRVAdapter(private val contestList: List<ActivityWithAccumulatedHour>): RecyclerView.Adapter<ContestRVAdapter.ContestViewHolder>(){

    override fun getItemCount(): Int {
        return contestList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContestViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = contestList[position].startDate ?: ""
        val title = contestList[position].title ?: ""
        val arwardType = contestList[position].award?.toString() ?: ""
        val index = contestList[position].reindex.toString() ?: ""

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(arwardType)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(index)
    }

    class ContestViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}