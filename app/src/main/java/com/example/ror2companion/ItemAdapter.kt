package com.example.ror2companion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.itementry.view.*
import java.util.*

class ItemAdapter: BaseAdapter {
    var itemList = ArrayList<Item>()
    var context: Context? = null

    constructor(context: Context, itemList: ArrayList<Item>) : super() {
        this.context = context
        this.itemList = itemList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = this.itemList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var itemView = inflator.inflate(R.layout.itementry, null)
        itemView.tag = position
        itemView.imgItem.setImageResource(item.image!!)


        return itemView
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }
}