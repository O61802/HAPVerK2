package com.example.enpit_p31.hapverk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class ScheduleAdapterAsa(data: OrderedRealmCollection<ScheduleAsa>?) : RealmBaseAdapter<ScheduleAsa>(data) {
    inner class ViewHolder(cell: View) {
        val data = cell.findViewById<TextView>(android.R.id.text1)
        val title = cell.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val view: View
        val viewHolder: ViewHolder

        when (convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent,false)
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            }
            else -> {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
        }
        adapterData?.run {
            val scheduleAsa = get(position)
            viewHolder.data.text = android.text.format.DateFormat.format("yyyy/MM/dd",scheduleAsa.date)
            viewHolder.title.text = scheduleAsa.title
        }
        return view
    }
}