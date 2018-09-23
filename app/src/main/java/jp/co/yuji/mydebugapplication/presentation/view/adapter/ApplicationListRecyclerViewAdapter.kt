package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.FilterableRecyclerViewAdapter

/**
 * Application List Adapter.
 */
class ApplicationListRecyclerViewAdapter(private val items: ArrayList<ApplicationListDto>) : FilterableRecyclerViewAdapter<ApplicationListDto>(items) {

    interface OnItemClickListener {
        fun onItemClick(packageName: String)
    }

    private var listener : OnItemClickListener? = null

    override fun updateList(list: List<ApplicationListDto>) {
        items.addAll(list)
        filteredList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_application_list_item, parent, false)
        return ViewHolder.create(v)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.icon?.setImageDrawable(filteredList[position].icon)
        holder?.appName?.text = filteredList[position].appName
        holder?.packageName?.text = filteredList[position].packageName
        holder?.itemView?.setOnClickListener { listener?.onItemClick(filteredList[position].packageName) }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var icon: ImageView? = itemView.findViewById(R.id.icon) as? ImageView
        var appName: TextView? = itemView.findViewById(R.id.app_name_text) as? TextView
        var packageName: TextView? = itemView.findViewById(R.id.package_name_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }

}