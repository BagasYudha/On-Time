package com.example.ontime.dosen

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.matkul.MatkulAdapter
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.setup.AppViewModel
import com.example.ontime.matkul.MataKuliah


class DosenFragment : Fragment() {
    private lateinit var binding: FragmentDosenBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var dosenAdapter: DosenAdapter
    private lateinit var matkulAdapter: MatkulAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDosenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Untuk Dosen
        dosenAdapter = DosenAdapter(listOf()) { dosen ->
            appViewModel.deleteDosen(dosen)
            Toast.makeText(requireContext(), "Dosen ${dosen.nama} berhasil dihapus", Toast.LENGTH_SHORT).show()
        }

        binding.rvDaftarDosen.adapter = dosenAdapter
        binding.rvDaftarDosen.layoutManager = LinearLayoutManager(requireContext())

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
                appViewModel.insertDosen(dosen)
                Toast.makeText(requireContext(), "Dosen $namaDosen berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

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
