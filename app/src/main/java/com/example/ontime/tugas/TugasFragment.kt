package com.example.ontime.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.databinding.FragmentTugasBinding
import com.example.ontime.setup.AppViewModel

class TugasFragment : Fragment() {

    private lateinit var binding: FragmentTugasBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var tugasAdapter: TugasAdapter
    private var matkulDipilih: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTugasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        tugasAdapter = TugasAdapter(listOf()) {tugas ->
            appViewModel.deleteTugas(tugas)

            Toast.makeText(requireContext(), "Dosen ${tugas.judul} sudah selesai", Toast.LENGTH_SHORT) .show()
        }

        // Data yang akan ditampilkan dalam Spinner
        val items = listOf("PAJ", "Mobile 2", "Jarkom 2", "Pancasila", "Bahasa Inggris", "Sistem Cerdas")

        // Buat ArrayAdapter untuk Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapter ke Spinner
        binding.mySpinner.adapter = adapter

        // Set listener untuk menangani pilihan spinner matkul
        binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                matkulDipilih = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                matkulDipilih = null
            }
        }

        binding.RvDaftarTugas.adapter = tugasAdapter
        binding.RvDaftarTugas.layoutManager = LinearLayoutManager(requireContext())

        // Observasi data dari ViewModel
        appViewModel.allTugas.observe(viewLifecycleOwner){ tugas ->
            tugasAdapter.updateTugas(tugas)
        }

        // Set action untuk button yang menambahkan tugas
        binding.roundButton.setOnClickListener {
            val tugasBaru = binding.InputTambahTugas.text.toString()

            if (tugasBaru.isNotEmpty() && matkulDipilih != null) {
                val tugas = Tugas(judul = tugasBaru, matkul = matkulDipilih!!, isDone = false)
                appViewModel.insertTugas(tugas)
                Toast.makeText(requireContext(), "Tugas baru ditambahkan: $tugasBaru", Toast.LENGTH_SHORT).show()
                binding.InputTambahTugas.text.clear()
            } else {
                Toast.makeText(requireContext(), "Mohon masukkan tugas dan pilih mata kuliah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}