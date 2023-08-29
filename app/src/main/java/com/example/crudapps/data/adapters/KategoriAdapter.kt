package com.example.crudapps.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.crudapps.R

class KategoriAdapter(context: Context, resource: Int, items: List<String>) :
    ArrayAdapter<String>(context, resource, items) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.kategori, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
