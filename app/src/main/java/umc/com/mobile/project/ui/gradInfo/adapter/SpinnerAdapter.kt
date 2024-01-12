package umc.com.mobile.project.ui.gradInfo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import umc.com.mobile.project.R
import umc.com.mobile.project.data.model.gradInfo.ScheduleSpinner
import umc.com.mobile.project.databinding.ItemSpinnerGradInfoScheduleBinding

class SpinnerAdapter(
	context: Context,
	@LayoutRes private val resId: Int,
	private val values: MutableList<ScheduleSpinner>
) : ArrayAdapter<ScheduleSpinner>(context, resId, values) {

	override fun getCount() = values.size


	override fun getItem(position: Int) = values[position]

	@SuppressLint("ViewHolder")
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val binding = ItemSpinnerGradInfoScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val model = values[position]
		try {
			binding.tvSchedule.text = model.name
			binding.tvSchedule.setTextColor(ContextCompat.getColor(context, R.color.white))
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return binding.root
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
		val binding = ItemSpinnerGradInfoScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val model = values[position]
		try {
			binding.tvSchedule.text = model.name

		} catch (e: Exception) {
			e.printStackTrace()
		}
		return binding.root
	}

}