package com.example.ontime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemMatkulBinding
import com.example.ontime.setup.MataKuliah

class MatkulAdapter(
    private var matkul: List<MataKuliah>,
    private val onDeleteClick: (MataKuliah) -> Unit
) :
    RecyclerView.Adapter<MatkulAdapter.MataKuliahViewHolder>() {

    inner class MataKuliahViewHolder(private val binding: ItemMatkulBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(matkul: MataKuliah) {
            binding.namaMatkul.text = matkul.matkul
            binding.sksMatkul.text = matkul.sks.toString()

            binding.btnDeleteMatkul.setOnClickListener {
                onDeleteClick(matkul)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MataKuliahViewHolder {
        val binding = ItemMatkulBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MataKuliahViewHolder(binding)
    }

    override fun getItemCount(): Int = matkul.size

    override fun onBindViewHolder(holder: MataKuliahViewHolder, position: Int) {
        holder.bind(matkul[position])
    }

    fun updateMataKuliah(newMataKuliah: List<MataKuliah>) {
        matkul = newMataKuliah.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

}