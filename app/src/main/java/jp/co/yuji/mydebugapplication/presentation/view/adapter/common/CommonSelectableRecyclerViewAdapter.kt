package jp.co.yuji.mydebugapplication.presentation.view.adapter.common

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto

/**
 * Common Selectable Adapter.
 */
class CommonSelectableRecyclerViewAdapter (private val context: Context, private val items: ArrayList<CommonDto>) : RecyclerView.Adapter<CommonSelectableRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonSelectableRecyclerViewAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_common_item, parent, false)
        return ViewHolder.create(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView?.text = items[position].title
        holder.valueTextView?.text = items[position].value

        holder.valueTextView?.requestFocus()
        holder.valueTextView?.isSelected = true

        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        if (position%2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.recycler_view_back))
        }
    }

    fun getItems():  ArrayList<CommonDto> {
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