package com.example.ontime.dosen

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.matkul.MatkulAdapter
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.setup.AppViewModel
import com.example.ontime.matkul.MataKuliah
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DosenFragment : Fragment() {

    private lateinit var binding: FragmentDosenBinding
    private lateinit var appViewModel: AppViewModel

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

        val dosenAdapter = DosenAdapter(mutableListOf(),
            onDeleteClick = { dosen ->
                appViewModel.deleteDosenVm(dosen)
            }
        )

        binding.rvDaftarDosen.adapter = dosenAdapter
        binding.rvDaftarDosen.layoutManager = LinearLayoutManager(context)

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

        // Observasi data Note dari ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            appViewModel.dosens.collectLatest { notes ->
                dosenAdapter.updateDosen(notes)
            }
        }

    }
}
