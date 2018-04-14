package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.content.Context
import android.hardware.Sensor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R

/**
 * Sensor Info Adapter.
 */
class SensorInfoRecyclerViewAdapter (private val context: Context, private val items: List<Sensor>) : RecyclerView.Adapter<SensorInfoRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(sensor: Sensor)
    }

    private var listener : OnItemClickListener? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SensorInfoRecyclerViewAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_sensor_info_item, parent, false)
        return ViewHolder.create(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.titleTextView?.text = items[position].name
        holder?.valueTextView?.text = items[position].type.toString()
        holder?.itemView?.setOnClickListener { listener?.onItemClick(items[position]) }
    }

    fun setOnItemClickListener(listener: SensorInfoRecyclerViewAdapter.OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView? = itemView.findViewById(R.id.title_text) as? TextView
        var valueTextView: TextView? = itemView.findViewById(R.id.value_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }
}