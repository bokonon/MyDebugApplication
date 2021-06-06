package jp.co.yuji.mydebugapplication.presentation.view.adapter.common

import androidx.recyclerview.widget.RecyclerView
import jp.co.yuji.mydebugapplication.presentation.view.adapter.ApplicationListRecyclerViewAdapter
import java.util.*
import kotlin.collections.ArrayList

abstract class FilterableRecyclerViewAdapter<T: MyFilterable>(private val items: ArrayList<T>) : RecyclerView.Adapter<ApplicationListRecyclerViewAdapter.ViewHolder>() {

    val filteredList = items.toMutableList()

    abstract fun updateList(list: List<T>)

    fun filter(char: CharSequence?) {
        if (char != null && char.isNotEmpty()) {
            val lowerConstraint = char.toString().toLowerCase(Locale.ROOT)
            filteredList.clear()
            for (filterable in items) {
                if (filterable.getFirstFilterName().toLowerCase(Locale.ROOT).contains(lowerConstraint)
                        || filterable.getSecondFilterName().toLowerCase(Locale.ROOT).contains(lowerConstraint)) {
                    filteredList.add(filterable)
                }
            }

        } else {
            filteredList.addAll(items.toMutableList())
        }
        notifyDataSetChanged()
    }
}