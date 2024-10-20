package com.example.ontime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemDosenBinding
import com.example.ontime.setup.Dosen

class DosenAdapter(
    private var dosen: List<Dosen>,
    private val onDeleteClick: (Dosen) -> Unit
) :
    RecyclerView.Adapter<DosenAdapter.DosenViewHolder>() {

    inner class DosenViewHolder(private val binding: ItemDosenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dosen: Dosen) {
            binding.NamaDosen.text = dosen.nama
            binding.Email.text = dosen.email

            binding.btnDelete.setOnClickListener {
                onDeleteClick(dosen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DosenViewHolder {
        val binding = ItemDosenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DosenViewHolder(binding)
    }

    override fun getItemCount(): Int = dosen.size

    override fun onBindViewHolder(holder: DosenViewHolder, position: Int) {
        holder.bind(dosen[position])
    }

    fun updateDosen(newDosen: List<Dosen>) {
        dosen = newDosen.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

}