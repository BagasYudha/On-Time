package com.example.ontime.dosen

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.matkul.MatkulAdapter
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.setup.AppViewModel
import com.example.ontime.matkul.MataKuliah


class DosenFragment : Fragment() {

    private lateinit var binding: FragmentDosenBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var dosenAdapter: DosenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDosenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Untuk Dosen
        dosenAdapter = DosenAdapter(listOf()) { dosen ->
            appViewModel.deleteDosenVm(dosen)
            Toast.makeText(requireContext(), "Dosen ${dosen.nama} berhasil dihapus", Toast.LENGTH_SHORT).show()
        }

        val customLayoutManager = GridLayoutManager(requireContext(), 2)

        // Menambahkan SpanSizeLookup untuk menentukan jumlah kolom yang digunakan
        customLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Menentukan jumlah kolom yang digunakan berdasarkan tipe data
                return when (val item = dosenAdapter.getItem(position)) {
                    is Dosen -> 1   // Jika item adalah tipe Dosen, gunakan 1 kolom
                    else -> 2        // Untuk tipe lainnya, gunakan 2 kolom
                }
            }
        }

        binding.rvDaftarDosen.adapter = dosenAdapter
        binding.rvDaftarDosen.layoutManager = customLayoutManager

        // Observasi data dari ViewModel
        appViewModel.allDosen.observe(viewLifecycleOwner) { dosen ->
            dosenAdapter.updateDosen(dosen)
        }

        // Tambahkan dosen baru
        binding.roundButton.setOnClickListener {
            val namaDosen = binding.TambahDataDosen.text.toString()
            val emailDosen = binding.EmailDosen.text.toString()

            if (namaDosen.isNotEmpty() && emailDosen.isNotEmpty()) {
                val dosen = Dosen(nama = namaDosen, email = emailDosen)
                appViewModel.insertDosenVm(dosen)
                Toast.makeText(requireContext(), "Dosen $namaDosen berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
