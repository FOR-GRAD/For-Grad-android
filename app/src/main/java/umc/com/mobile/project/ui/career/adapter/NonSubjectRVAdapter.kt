package umc.com.mobile.project.ui.career.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.project.data.model.career.PointDtoList
import umc.com.mobile.project.data.model.career.Result
import umc.com.mobile.project.databinding.ItemCertificateBinding

class NonSubjectRVAdapter(private val response: Result?): RecyclerView.Adapter<NonSubjectRVAdapter.NonSubjectViewHolder>(){
    private val nonsubjectList: ArrayList<PointDtoList> = response?.pointDtoList as ArrayList<PointDtoList>

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
        holder.itemBinding.etCertificateType.text = editableFactory.newEditable(nonsubjectList[position].rewardPoints)
        holder.itemBinding.etCertificateRating.text = editableFactory.newEditable(nonsubjectList[position].accumulatedPoints)
    }

    class NonSubjectViewHolder(val itemBinding: ItemCertificateBinding) : RecyclerView.ViewHolder(itemBinding.root)
}