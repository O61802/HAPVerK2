package com.example.enpit_p31.hapverk

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class SAbaitarub(data: OrderedRealmCollection<SB>?) : RealmBaseAdapter<SB>(data) {
    inner class ViewHolderB(cellB: View) {
        val monce = cellB.findViewById<TextView>(android.R.id.text1)
        val hikaigosya = cellB.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolderB:ViewHolderB

        when (convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2,parent,false)
                viewHolderB = ViewHolderB(view)
                view.tag = viewHolderB
            }
            else -> {
                view = convertView
                viewHolderB = view.tag as ViewHolderB
            }
        }
        adapterData?.run {
            val scheduleB = get(position)
            viewHolderB.monce.text = DateFormat.format("yyyy/MM/dd", scheduleB.monce)
            viewHolderB.hikaigosya.text = scheduleB.hikaigosya
        }
        return  view
    }
}