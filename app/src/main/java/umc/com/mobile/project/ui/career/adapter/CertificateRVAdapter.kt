package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.CertificateDto

class CertificateRVAdapter: PagingDataAdapter<CertificateDto, CertificateRVAdapter.CertificateViewHolder>(
    CertificateRVAdapter.DiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CertificateRVAdapter.CertificateViewHolder {
        val itemBinding =
            ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateRVAdapter.CertificateViewHolder(itemBinding)
    }

/*    override fun getItemCount(): Int {
        return certificateList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateViewHolder(itemBinding)
    }*/

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val certificate = getItem(position)
        certificate?.let {
            val editableFactory = Editable.Factory.getInstance()

            holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(it.date)
            holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(it.title)
            holder.itemBinding.etCertificateType.text = editableFactory.newEditable(it.type)
            holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(it.rating)
        }
    }

    class CertificateViewHolder(val itemBinding: ItemCertificateBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private class DiffCallback : DiffUtil.ItemCallback<CertificateDto>() {
        override fun areItemsTheSame(
            oldItem: CertificateDto,
            newItem: CertificateDto
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: CertificateDto,
            newItem: CertificateDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}