package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.ActivityDto

class ActivityRVAdapter(private val activityList: ArrayList<ActivityDto>): RecyclerView.Adapter<ActivityRVAdapter.ActivityViewHolder>(){

    override fun getItemCount(): Int {
        return activityList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(activityList[position].date)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(activityList[position].title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(activityList[position].point)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(activityList[position].accumulatedPoint)
    }

    class ActivityViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}