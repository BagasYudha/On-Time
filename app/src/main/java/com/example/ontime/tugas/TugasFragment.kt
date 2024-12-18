package com.example.ontime.tugas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ontime.databinding.FragmentTugasBinding
import com.example.ontime.setup.AppViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TugasFragment : Fragment() {

    private lateinit var binding: FragmentTugasBinding
    private lateinit var appViewModel: AppViewModel
//    private lateinit var tugasAdapter: TugasAdapter
    private var matkulDipilih: String? = null

    companion object {
        private const val TAG = "TugasFragment" // Untuk log di Logcat
    }

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

        val tugasAdapter = TugasAdapter(
            onDeleteClick = { tugas ->
                appViewModel.markTugasCompleteVm(tugas)
                Toast.makeText(
                    requireContext(),
                    "Tugas ${tugas.judul} sudah ditandai sebagai selesai",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onHoldClick = { tugas ->
                appViewModel.deleteTugasVm(tugas)
                Toast.makeText(
                    requireContext(),
                    "Tugas ${tugas.judul} telah dihapus",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.RvDaftarTugas.adapter = tugasAdapter
        binding.RvDaftarTugas.layoutManager = GridLayoutManager(context, 2)

        // Observasi data dari ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            appViewModel.tugasBelumSelesai.collectLatest { tugas ->
                Log.d("TugasFragment", "Tugas belum selesai: $tugas")
                tugasAdapter.updateTugas(tugas)
            }
        }


        // Set action untuk button yang menambahkan tugas
        binding.roundButton.setOnClickListener {
            val tugas = binding.InputTambahTugas.text.toString()
            val priority = binding.priorityCheckBox.isChecked

            if (tugas.isNotEmpty() && matkulDipilih != null) {
                val tugasBaru =
                    Tugas(judul = tugas, matkul = matkulDipilih!!, done = false, isPriority = priority)
                appViewModel.insertTugasVm(tugasBaru)
                Toast.makeText(
                    requireContext(),
                    "Tugas baru ditambahkan: $tugasBaru",
                    Toast.LENGTH_SHORT
                ).show()
                binding.InputTambahTugas.text.clear()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Mohon masukkan tugas dan pilih mata kuliah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Data yang akan ditampilkan dalam Spinner
        val items =
            listOf("PAJ", "Mobile 2", "Jarkom 2", "Pancasila", "Bahasa Inggris", "Sistem Cerdas")

        // Buat ArrayAdapter untuk Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapter ke Spinner
        binding.mySpinner.adapter = adapter

        // Set listener untuk menangani pilihan spinner matkul
        binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                matkulDipilih = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                matkulDipilih = null
            }
        }
    }
}
