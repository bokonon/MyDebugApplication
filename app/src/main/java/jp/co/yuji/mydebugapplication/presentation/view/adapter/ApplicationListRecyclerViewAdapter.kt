package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto

/**
 * Application List Adapter.
 */
class ApplicationListRecyclerViewAdapter(private val items: ArrayList<ApplicationListDto>) : RecyclerView.Adapter<ApplicationListRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(packageName: String)
    }

    private var listener : OnItemClickListener? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_application_list_item, parent, false)
        return ViewHolder.create(v)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.icon?.setImageDrawable(items[position].icon)
        holder?.appName?.text = items[position].appName
        holder?.packageName?.text = items[position].packageName
        holder?.itemView?.setOnClickListener { listener?.onItemClick(items[position].packageName) }
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