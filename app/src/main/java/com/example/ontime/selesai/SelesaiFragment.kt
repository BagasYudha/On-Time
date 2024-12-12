package com.example.ontime.selesai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ontime.databinding.FragmentSelesaiBinding
import com.example.ontime.setup.AppViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
            viewLifecycleOwner.lifecycleScope.launch {
                appViewModel.markTugasIncompleteVm(tugas)
            }
        }

        binding.rvDaftarSelesai.adapter = selesaiAdapter
        binding.rvDaftarSelesai.layoutManager = GridLayoutManager(requireContext(), 2)

        // Amati StateFlow tugas selesai dari Firebase
        viewLifecycleOwner.lifecycleScope.launch {
            appViewModel.tugasSelesai.collect { tugasList ->
                selesaiAdapter.updateTugas(tugasList)
            }
        }
    }
}

