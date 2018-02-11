package jp.co.yuji.mydebugapplication.presentation.view.adapter

import android.content.Context
import android.net.wifi.ScanResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R

/**
 * WiFi Info List RecyclerViewAdapter.
 */
class WiFiInfoListRecyclerViewAdapter(private val items: ArrayList<ScanResult>) : RecyclerView.Adapter<WiFiInfoListRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(scanResult: ScanResult)
    }

    private var listener : WiFiInfoListRecyclerViewAdapter.OnItemClickListener? = null

    private var context: Context? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v: View = LayoutInflater.from(context)
                .inflate(R.layout.adapter_wifi_info_list_item, parent, false)
        return ViewHolder.create(v)
    }

    fun setOnItemClickListener(listener: WiFiInfoListRecyclerViewAdapter.OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ssidText?.text = items[position].SSID
        holder.macAddressText?.text = items[position].BSSID
        holder.capabilitiesText?.text = items[position].capabilities
        holder.frequencyText?.text = context?.getString(R.string.wifi_info_frequency_unit_text, items[position].frequency)
        holder.levelText?.text = context?.getString(R.string.wifi_info_level_unit_text, items[position].level)
        holder.itemView?.setOnClickListener { listener?.onItemClick(items[position]) }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var ssidText: TextView? = itemView.findViewById(R.id.ssid_text) as? TextView
        var macAddressText: TextView? = itemView.findViewById(R.id.mac_address_text) as? TextView
        var capabilitiesText: TextView? = itemView.findViewById(R.id.capabilities_text) as? TextView
        var frequencyText: TextView? = itemView.findViewById(R.id.frequency_text) as? TextView
        var levelText: TextView? = itemView.findViewById(R.id.level_text) as? TextView

        companion object Factory {
            fun create(v: View): ViewHolder = ViewHolder(v)
        }
    }

}