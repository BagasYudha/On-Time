package com.example.ontime.tugas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemTugasBinding

class TugasAdapter(
    private var tugas: List<Tugas>,
    private val onDeleteClick: (Tugas) -> Unit
) : RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    inner class TugasViewHolder(private val binding: ItemTugasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tugas: Tugas) {
            binding.tvNamaTugas.text = tugas.judul
            binding.tvMataKuliah.text= tugas.matkul

            binding.itemTugas.setOnClickListener{
                onDeleteClick(tugas)
            }
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
        tugas = newTugas.sortedByDescending { it.id }
        notifyDataSetChanged()
    }
}