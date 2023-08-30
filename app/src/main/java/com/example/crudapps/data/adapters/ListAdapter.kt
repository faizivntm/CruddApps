package com.example.crudapps.firebases.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crudapps.R
import com.example.crudapps.databinding.ActivityHomeBinding
import com.example.crudapps.models.bookModel
import com.example.crudapps.ui.detail.DetailActivity
import com.example.crudapps.ui.detail.DetailViewModel
import com.example.crudapps.ui.update.UpdateActivity

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var data: List<bookModel> = emptyList()

    fun updateData(newData: List<bookModel>) {
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
            intent.putExtra("judul", currentItem.judul)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ActivityHomeBinding = ActivityHomeBinding.bind(itemView)

        fun bind(item: bookModel) {
            binding.judulBuku.text = item.judul
            binding.penulisBuku.text = item.namaPenulis

            Glide.with(binding.root)
                .load(item.urlImage)
                .into(binding.coverBuku)
        }
    }
}
