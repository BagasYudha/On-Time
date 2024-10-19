package com.example.ontime.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.adapter.DosenAdapter
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.setup.AppViewModel
import com.example.ontime.setup.Dosen


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

        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        dosenAdapter = DosenAdapter(listOf())

        binding.rvDaftarDosen.adapter = dosenAdapter
        binding.rvDaftarDosen.layoutManager = LinearLayoutManager(requireContext())

        // Observasi data dari ViewModel
        appViewModel.allDosen.observe(viewLifecycleOwner){ dosen ->
            dosenAdapter.updateDosen(dosen)
        }

        // Tambahkan tugas baru
        binding.roundButton.setOnClickListener {
            val namaDosen = binding.TambahDataDosen.text.toString()
            val emailDosen = binding.EmailDosen.text.toString()
//            val mataKuliah = binding.MataKuliah.text.toString()

            if (namaDosen.isNotEmpty() && emailDosen.isNotEmpty()) {
                val dosen = Dosen(nama = namaDosen, email = emailDosen)
                appViewModel.insertDosen(dosen) // Memanggil method untuk menyimpan dosen
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

    }

}