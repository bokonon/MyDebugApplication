package jp.co.yuji.mydebugapplication.presentation.view.adapter.common

import android.support.v7.widget.RecyclerView
import jp.co.yuji.mydebugapplication.presentation.view.adapter.ApplicationListRecyclerViewAdapter

abstract class FilterableRecyclerViewAdapter<T: MyFilterable>(private val items: ArrayList<T>) : RecyclerView.Adapter<ApplicationListRecyclerViewAdapter.ViewHolder>() {

    val filteredList = items.toMutableList()

    abstract fun updateList(list: List<T>)

    fun filter(char: CharSequence?) {
        if (char != null && char.isNotEmpty()) {
            val lowerConstraint = char.toString().toLowerCase()
            filteredList.clear()
            for (filterable in items) {
                if (filterable.getFirstFilterName().toLowerCase().contains(lowerConstraint)
                        || filterable.getSecondFilterName().toLowerCase().contains(lowerConstraint)) {
                    filteredList.add(filterable)
                }
            }

        } else {
            filteredList.addAll(items.toMutableList())
        }
        notifyDataSetChanged()
    }
}