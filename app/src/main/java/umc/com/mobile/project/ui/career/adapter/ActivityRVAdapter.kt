package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemActivityBinding

class ActivityRVAdapter(private val activityList: List<ActivityWithAccumulatedHour>) :
    RecyclerView.Adapter<ActivityRVAdapter.ActivityViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return activityList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemBinding =
            ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = activityList[position].startDate ?: ""
        val title = activityList[position].title ?: ""
        val index = activityList[position].reindex.toString() ?: ""

        holder.itemBinding.etActivityDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etActivityTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etActivityIndex.text = editableFactory.newEditable(index)
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(position)
        }
    }

    class ActivityViewHolder(val itemBinding: ItemActivityBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}