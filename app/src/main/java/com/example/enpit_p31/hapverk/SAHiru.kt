package com.example.enpit_p31.hapverk

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class SAHiru(data: OrderedRealmCollection<SH>?) : RealmBaseAdapter<SH>(data) {
    inner class ViewHolderhiru(cellhiru: View){
        val day = cellhiru.findViewById<TextView>(android.R.id.text1)
        val mei = cellhiru.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolderhiru: ViewHolderhiru

        when (convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent,false)
                viewHolderhiru = ViewHolderhiru(view)
                view.tag = viewHolderhiru
            }
            else -> {
                view = convertView
                viewHolderhiru = view.tag as ViewHolderhiru
            }
        }

        adapterData?.run {
            val SH = get(position)
            viewHolderhiru.day.text =
                    DateFormat.format("yyyy/MM/dd", SH.day)
            viewHolderhiru.mei.text = SH.mei
        }
        return view
    }
}