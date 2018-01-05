package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R

/**
 * Created by yuji on 2018/01/04.
 */
class HardwareInfoRecyclerViewAdapter(private val items: List<String>) : RecyclerView.Adapter<HardwareInfoRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener : OnItemClickListener? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_hardware_info_item, parent, false)
        return ViewHolder.create(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.titleTextView?.text = items[position]
        holder?.itemView?.setOnClickListener { listener?.onItemClick(position) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView? = itemView.findViewById(R.id.title_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }

}