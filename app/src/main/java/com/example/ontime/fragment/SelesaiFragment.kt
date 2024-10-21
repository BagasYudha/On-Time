package com.example.ontime.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ontime.R
import com.example.ontime.adapter.SelesaiAdapter
import com.example.ontime.adapter.TugasAdapter
import com.example.ontime.databinding.FragmentDosenBinding
import com.example.ontime.databinding.FragmentSelesaiBinding
import com.example.ontime.setup.AppViewModel

class SelesaiFragment : Fragment() {

    private lateinit var binding: FragmentSelesaiBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var selesaiAdapter: SelesaiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelesaiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        selesaiAdapter = SelesaiAdapter(emptyList()) { tugas ->
            // Panggil fungsi untuk mengubah status tugas menjadi tidak selesai
            appViewModel.incompleteTugas(tugas)
        }

        binding.rvDaftarSelesai.adapter = selesaiAdapter
        binding.rvDaftarSelesai.layoutManager = LinearLayoutManager(requireContext())

        // Observasi data dari ViewModel
        appViewModel.allTugas.observe(viewLifecycleOwner) { tugas ->
            selesaiAdapter.updateTugas(tugas)
        }

        // Amati LiveData untuk tugas yang sudah selesai
        appViewModel.tugasSelesai.observe(viewLifecycleOwner) { tugasList ->
            selesaiAdapter.updateTugas(tugasList)
        }
    }
}