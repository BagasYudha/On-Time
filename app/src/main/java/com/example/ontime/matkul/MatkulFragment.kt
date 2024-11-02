package com.example.ontime.matkul

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.R
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.databinding.FragmentMatkulBinding
import com.example.ontime.dosen.Dosen
import com.example.ontime.dosen.DosenAdapter
import com.example.ontime.setup.AppViewModel


class MatkulFragment : Fragment() {

    private lateinit var binding: FragmentMatkulBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var matkulAdapter: MatkulAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatkulBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Untuk Matkul
        matkulAdapter = MatkulAdapter(listOf()) { matkul ->
            appViewModel.deleteMatkul(matkul)
            Toast.makeText(requireContext(), "Mata Kuliah ${matkul.matkul} berhasil dihapus", Toast.LENGTH_SHORT).show()
        }

        binding.rvMatkul.adapter = matkulAdapter
        binding.rvMatkul.layoutManager = LinearLayoutManager(requireContext())

        // Observasi data dari ViewModel
        appViewModel.allMatkul.observe(viewLifecycleOwner) { matkulList ->
            matkulAdapter.updateMataKuliah(matkulList)
        }

        // Tambahkan matkul baru
        binding.roundButtonMatkul.setOnClickListener {
            val matkul = binding.etNamaMatkul.text.toString()
            val sks = binding.etSks.text.toString()

            if (matkul.isNotEmpty() && sks.isNotEmpty()) {
                val matkulObj = MataKuliah(matkul = matkul, sks = sks.toInt()) // Pastikan menggunakan kelas yang benar
                appViewModel.insertMatkul(matkulObj)
                Toast.makeText(requireContext(), "Mata Kuliah $matkul berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

    }
}