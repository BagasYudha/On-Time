package com.example.ontime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemTugasBinding
import com.example.ontime.setup.Tugas

class TugasAdapter(
    private var tugas: List<Tugas>,
    private val onDeleteClick: (Tugas) -> Unit
) : RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    inner class TugasViewHolder(private val binding: ItemTugasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tugas: Tugas) {
            binding.tvNamaTugas.text = tugas.judul // Sesuaikan dengan nama properti di model Tugas Anda
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val binding = ItemTugasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TugasViewHolder(binding)
    }

    override fun getItemCount(): Int = tugas.size

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        holder.bind(tugas[position])
    }

    fun updateTugas(newTugas: List<Tugas>) {
        tugas = newTugas.sortedByDescending { it.id } // Mengurutkan berdasarkan ID secara menurun
        notifyDataSetChanged() // Memberitahu adapter bahwa data telah berubah
    }
}
