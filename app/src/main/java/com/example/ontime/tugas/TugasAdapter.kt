package com.example.ontime.tugas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ontime.databinding.ItemTugasBinding
import com.example.ontime.databinding.ItemTugasPriorityBinding
import com.example.ontime.setup.AppViewModel

enum class ITEM_VIEW_TYPE { NORMAL, PRIORITY }

class TugasAdapter(
    private var tugas: List<Tugas>,
    private val onDeleteClick: (Tugas) -> Unit,
    private val onHoldClick: (Tugas) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TugasViewHolder(private val binding: ItemTugasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tugas: Tugas) {
            binding.tvNamaTugas.text = tugas.judul
            binding.tvMataKuliah.text = tugas.matkul

            binding.itemTugas.setOnClickListener {
                onDeleteClick(tugas)
            }
            binding.itemTugas.setOnLongClickListener {
                onHoldClick(tugas)
                true
            }
        }
    }

    inner class TugasPriorityViewHolder(private val binding: ItemTugasPriorityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tugas: Tugas) {
            binding.tvNamaTugas.text = tugas.judul
            binding.tvMataKuliah.text = tugas.matkul

            binding.itemTugasPeriority.setOnClickListener {
                onDeleteClick(tugas)
            }
            binding.itemTugasPeriority.setOnLongClickListener {
                onHoldClick(tugas)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (tugas[position].isPrority) {
            ITEM_VIEW_TYPE.PRIORITY.ordinal
        } else {
            ITEM_VIEW_TYPE.NORMAL.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE.PRIORITY.ordinal -> {
                val binding = ItemTugasPriorityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TugasPriorityViewHolder(binding)
            }

            else -> {
                val binding = ItemTugasBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TugasViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = tugas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tugasBind = tugas[position]

        when (holder) {
            is TugasViewHolder -> holder.bind(tugasBind)
            is TugasPriorityViewHolder -> holder.bind(tugasBind)
        }
    }

    fun updateTugas(newTugas: List<Tugas>) {
        tugas = newTugas.sortedByDescending { it.id }
        notifyDataSetChanged()
    }
}