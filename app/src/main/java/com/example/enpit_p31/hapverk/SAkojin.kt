package com.example.enpit_p31.hapverk

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class SAkojin(data: OrderedRealmCollection<SK>?) : RealmBaseAdapter<SK>(data) {
    inner class ViewHolderK(cellK:View) {
        val year = cellK.findViewById<TextView>(android.R.id.text1)
        val keiyakusya = cellK.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val viewHolderK: SAkojin.ViewHolderK

        when (convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                viewHolderK = ViewHolderK(view)
                view.tag = viewHolderK
            }
            else -> {
                view = convertView
                viewHolderK = view.tag as ViewHolderK
            }
        }
        adapterData?.run {
            val scheduleAsa = get(position)
            viewHolderK.year.text=
                    DateFormat.format("yyyy/MM/dd", scheduleAsa.year)
            viewHolderK.keiyakusya.text = scheduleAsa.keiyakusya
        }
        return view
    }
}