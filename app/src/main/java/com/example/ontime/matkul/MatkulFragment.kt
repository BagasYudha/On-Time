package com.example.ontime.matkul

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
import com.example.ontime.databinding.FragmentMatkulBinding
import com.example.ontime.setup.AppViewModel
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MatkulFragment : Fragment() {

    private lateinit var binding: FragmentMatkulBinding
    private lateinit var appViewModel: AppViewModel

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
        val matkulAdapter = MatkulAdapter(mutableListOf(),
            onDeleteClick = { matkul ->
                appViewModel.deleteMatkulVm(matkul)
            }
        )
        binding.rvMatkul.adapter = matkulAdapter
        binding.rvMatkul.layoutManager = LinearLayoutManager(context)

        binding.roundButtonMatkul.setOnClickListener {
            val matkul = binding.etNamaMatkul.text.toString()
            val sks = binding.etSks.text.toString()

            if (matkul.isNotEmpty() && sks.isNotEmpty()) {
                val matkul = MataKuliah(matkul = matkul, sks = sks)
                appViewModel.insertMatkulVm(matkul)
                Toast.makeText(requireContext(), "Mata Kuliah $matkul berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            appViewModel.matkuls.collectLatest { notes ->
                matkulAdapter.updateMataKuliah(notes)
            }
        }
    }

    }
