package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.databinding.ItemCertificateBinding
import umc.com.mobile.project.ui.career.data.ContestDto

class ContestRVAdapter(private val contestList: ArrayList<ContestDto>): RecyclerView.Adapter<ContestRVAdapter.ContestViewHolder>(){

    override fun getItemCount(): Int {
        return contestList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val itemBinding = ItemCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContestViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val editableFactory = Editable.Factory.getInstance()

        holder.itemBinding.etCertificateDate.text = editableFactory.newEditable(contestList[position].date)
        holder.itemBinding.etCertificateTitle.text = editableFactory.newEditable(contestList[position].title)
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(contestList[position].type)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(contestList[position].award)
    }

    class ContestViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}