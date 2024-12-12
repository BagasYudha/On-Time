package com.example.ontime.selesai

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemSelesaiBinding
import com.example.ontime.tugas.Tugas

class SelesaiAdapter(

    private var tugas: List<Tugas>,
    private val onStatusChange: (Tugas) -> Unit

) : RecyclerView.Adapter<SelesaiAdapter.SelesaiViewHolder>() {

    inner class SelesaiViewHolder(private val binding: ItemSelesaiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tugas: Tugas) {
            binding.namaTugas.text = tugas.judul
            binding.mataKuliah.text = tugas.matkul

            binding.itemSelesaiBox.setOnClickListener {
                onStatusChange(tugas)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelesaiViewHolder {
        val binding = ItemSelesaiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelesaiViewHolder(binding)
    }

    override fun getItemCount(): Int = tugas.size

    override fun onBindViewHolder(holder: SelesaiViewHolder, position: Int) {
        holder.bind(tugas[position])
    }

    fun updateTugas(newTugas: List<Tugas>) {
        tugas = newTugas.filter { it.done }
        notifyDataSetChanged()
    }
}