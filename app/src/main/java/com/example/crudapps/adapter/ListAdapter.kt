package com.example.crudapps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crudapps.R
import com.example.crudapps.databinding.ActivityHomeBinding
import com.example.crudapps.model.GetModel
import com.example.crudapps.view.DetailActivity

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var data: List<GetModel> = emptyList()

    fun updateData(newData: List<GetModel>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_home, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)

        // Menambahkan event klik pada item
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, currentItem.judul, Toast.LENGTH_SHORT).show()

            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)

            //mengirim data dari home ke detail
            intent.putExtra("urlImage", currentItem.urlImage)
            intent.putExtra("judul", currentItem.judul)
            intent.putExtra("namaPenulis", currentItem.namaPenulis)
            intent.putExtra("kategori", currentItem.kategori)
            intent.putExtra("tahunTerbit", currentItem.tahunTerbit)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ActivityHomeBinding = ActivityHomeBinding.bind(itemView)

        fun bind(item: GetModel) {
            binding.judulBuku.text = item.judul
            binding.penulisBuku.text = item.namaPenulis

            Glide.with(binding.root)
                .load(item.urlImage)
                .into(binding.coverBuku)
        }
    }
}
