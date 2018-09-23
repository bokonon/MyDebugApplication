package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto

/**
 * Battery Info Adapter.
 */
class BatteryInfoRecyclerViewAdapter (private val context: Context, private val items: ArrayList<CommonDto>) : RecyclerView.Adapter<BatteryInfoRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BatteryInfoRecyclerViewAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_battery_info_item, parent, false)
        return ViewHolder.create(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.titleTextView?.text = items[position].title
        holder?.valueTextView?.text = items[position].value

        holder?.valueTextView?.requestFocus()
        holder?.valueTextView?.isSelected = true

        holder?.itemView?.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        if (position%2 == 0) {
            holder?.itemView?.setBackgroundColor(ContextCompat.getColor(context, R.color.recycler_view_back))
        }
    }

    fun getList() :  ArrayList<CommonDto> {
        return items
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView? = itemView.findViewById(R.id.title_text) as? TextView
        var valueTextView: TextView? = itemView.findViewById(R.id.value_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }

}