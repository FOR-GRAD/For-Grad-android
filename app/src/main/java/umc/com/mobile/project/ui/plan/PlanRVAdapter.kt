package umc.com.mobile.project.ui.plan

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemCertificateBinding

class PlanRVAdapter(private val activityList: List<ActivityWithAccumulatedHour>): RecyclerView.Adapter<PlanRVAdapter.ActivityViewHolder>(){

    override fun getItemCount(): Int {
        return activityList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = activityList[position].startDate ?: ""
        val title = activityList[position].title ?: ""
        val index = activityList[position].reindex.toString() ?: ""

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(index)
    }

    class ActivityViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}