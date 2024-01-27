package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.CertificateDto

class NonSubjectRVAdapter(private val nonsubjectList: ArrayList<CertificateDto>): RecyclerView.Adapter<NonSubjectRVAdapter.NonSubjectViewHolder>(){

    override fun getItemCount(): Int {
        return nonsubjectList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonSubjectRVAdapter.NonSubjectViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NonSubjectRVAdapter.NonSubjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NonSubjectRVAdapter.NonSubjectViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(nonsubjectList[position].date)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(nonsubjectList[position].title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(nonsubjectList[position].type)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(nonsubjectList[position].rating)
    }

    class NonSubjectViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private class DiffCallback : DiffUtil.ItemCallback<CertificateDto>() {
        override fun areItemsTheSame(oldItem: CertificateDto, newItem: CertificateDto): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CertificateDto, newItem: CertificateDto): Boolean {
            return oldItem == newItem
        }
    }
}