package com.example.ontime.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ontime.adapter.TugasAdapter // Ganti dengan adapter yang sesuai jika ada
import com.example.ontime.databinding.FragmentTugasBinding
import com.example.ontime.setup.AppViewModel
import com.example.ontime.setup.Tugas

class TugasFragment : Fragment() {

    private lateinit var binding: FragmentTugasBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var tugasAdapter: TugasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTugasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Data yang akan ditampilkan dalam Spinner
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")

        // Buat ArrayAdapter untuk Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapter ke Spinner
        binding.mySpinner.adapter = adapter

        // Set listener untuk menangani pilihan item
        binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Tidak ada yang dipilih
            }
        }

        // Set action untuk button yang menambahkan tugas
        binding.roundButton.setOnClickListener {
            val tugasBaru = binding.InputTambahTugas.text.toString()

            if (tugasBaru.isNotEmpty()) {
                val tugas = Tugas(judul = tugasBaru, isDone = true)
                appViewModel.insertTugas(tugas)
                Toast.makeText(requireContext(), "Tugas baru ditambahkan: $tugasBaru", Toast.LENGTH_SHORT).show()
                binding.InputTambahTugas.text.clear()
            } else {
                Toast.makeText(requireContext(), "Mohon masukkan tugas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
