package com.example.enpit_p31.hapverk

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class SAyoru(data: OrderedRealmCollection<SY>?) : RealmBaseAdapter<SY>(data) {
    inner class ViewHolderyoru(cellyoru:View) {
        val day2 = cellyoru.findViewById<TextView>(android.R.id.text1)
        val mei2 = cellyoru.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val viewHolderyoru:ViewHolderyoru

        when (convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                viewHolderyoru = ViewHolderyoru(view)
                view.tag = viewHolderyoru
            }
            else -> {
                view = convertView
                viewHolderyoru = view.tag as ViewHolderyoru
            }
            }
        adapterData?.run {
            val scheduleAsa = get(position)
            viewHolderyoru.day2.text=
                    DateFormat.format("yyyy/MM/dd", scheduleAsa.day2)
            viewHolderyoru.mei2.text = scheduleAsa.mei2
        }
        return view
        }
}