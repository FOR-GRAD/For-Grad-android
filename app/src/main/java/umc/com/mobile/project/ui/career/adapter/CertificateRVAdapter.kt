package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.ActivityWithAccumulatedHour
import umc.com.mobile.project.databinding.ItemCertificateBinding

class CertificateRVAdapter(private var certificateList: List<ActivityWithAccumulatedHour>) :
    RecyclerView.Adapter<CertificateRVAdapter.CertificateViewHolder>() {

    fun updateItems(newItems: List<ActivityWithAccumulatedHour>) {
        certificateList = newItems
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return certificateList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val itemBinding =
            ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        val startDate = certificateList[position].startDate ?: ""
        val title = certificateList[position].title ?: ""
        val certificationType = certificateList[position].certificationType?.toString() ?: ""
        val reindex = certificateList[position].reindex.toString() ?: ""

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(startDate)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(mapType(certificationType))
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(reindex)
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(position)
        }
    }

    class CertificateViewHolder(val itemBinding: ItemCertificateBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private fun mapType(updatedType: String): String {
        return when (updatedType) {
            "PRACTICAL_EXAM" -> "실기"
            "WRITTEN_EXAM" -> "필기"
            else -> "면접"
        }
    }
}