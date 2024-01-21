package umc.com.mobile.project.ui.career.adapter

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.CertificateDto

class CertificateRVAdapter(private val certificateList: ArrayList<CertificateDto>): RecyclerView.Adapter<CertificateRVAdapter.CertificateViewHolder>(){

    override fun getItemCount(): Int {
        return certificateList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(certificateList[position].date)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(certificateList[position].title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(certificateList[position].type)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(certificateList[position].rating)
    }

    class CertificateViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}