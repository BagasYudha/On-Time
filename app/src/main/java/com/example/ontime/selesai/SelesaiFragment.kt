package com.example.ontime.selesai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
            appViewModel.markTugasIncompleteVm(tugas)
        }

        binding.rvDaftarSelesai.adapter = selesaiAdapter
        binding.rvDaftarSelesai.layoutManager = GridLayoutManager(requireContext(), 2) // Mengatur 2 kolom

        // Amati LiveData untuk tugas yang sudah selesai
        appViewModel.tugasSelesai.observe(viewLifecycleOwner) { tugasList ->
            selesaiAdapter.updateTugas(tugasList)
        }
    }
}
