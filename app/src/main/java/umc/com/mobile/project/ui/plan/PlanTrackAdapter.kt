package umc.com.mobile.project.ui.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.plan.PlanTrackResponse
import umc.com.mobile.project.data.model.plan.TrackResult
import umc.com.mobile.project.data.model.plan.semesterResult
import umc.com.mobile.project.databinding.ItemChooseSemesterBinding
import umc.com.mobile.project.databinding.ItemChooseTrackBinding

class PlanTrackAdapter(trackList: List<TrackResult?>, private val onItemClick: (TrackResult) -> Unit) : RecyclerView.Adapter<PlanTrackAdapter.NonSubjectViewHolder>() {

    var trackList: List<TrackResult?> = trackList
        set(value) {
            field = value
            notifyDataSetChanged()
            //데이터가 바뀌었다 알려줌.
        }



    override fun getItemCount(): Int {
        return trackList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanTrackAdapter.NonSubjectViewHolder {
        val itemBinding = ItemChooseTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanTrackAdapter.NonSubjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlanTrackAdapter.NonSubjectViewHolder, position: Int) {

        val trackInfo = trackList[position]
        if (trackInfo != null) {
            holder.itemBinding.planTimeTrackItem.text = trackInfo.trackName
        }

        holder.itemBinding.root.setOnClickListener {
            if (trackInfo != null) {
                onItemClick(trackInfo)
            }
        }






    }

    class NonSubjectViewHolder(val itemBinding: ItemChooseTrackBinding) : RecyclerView.ViewHolder(itemBinding.root)
}