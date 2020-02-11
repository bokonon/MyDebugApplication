package jp.co.yuji.mydebugapplication.presentation.view.adapter.common

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
 * Common Adapter.
 */
class CommonRecyclerViewAdapter(private val context : Context, items: ArrayList<CommonDto>) : RecyclerView.Adapter<CommonRecyclerViewAdapter.ViewHolder>() {

    val items = items

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_common_item, parent, false)
        return ViewHolder.create(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView?.text = items[position].title
        holder.valueTextView?.text = items[position].value

        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        if (position%2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.recycler_view_back))
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView? = itemView.findViewById(R.id.title_text) as? TextView
        var valueTextView: TextView? = itemView.findViewById(R.id.value_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }

}