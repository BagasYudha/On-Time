package com.example.ontime.selesai

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ontime.databinding.FragmentSelesaiBinding
import com.example.ontime.login.SignUpActivity
import com.example.ontime.setup.AppViewModel
import com.google.firebase.auth.FirebaseAuth
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

        selesaiAdapter = SelesaiAdapter(
            onStatusChange = { tugas ->
                appViewModel.markTugasIncompleteVm(tugas)

                Toast.makeText(
                    requireContext(),
                    "Tugas ${tugas.judul} sudah ditandai sebagai belum selesai",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.rvDaftarSelesai.adapter = selesaiAdapter
        binding.rvDaftarSelesai.layoutManager = GridLayoutManager(requireContext(), 2)

        // Amati StateFlow tugas selesai dari Firebase
        viewLifecycleOwner.lifecycleScope.launch {
            appViewModel.tugasSelesai.collect { tugasList ->
                selesaiAdapter.updateTugas(tugasList)
            }
        }

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut() // Logout dari Firebase
        val intent = Intent(requireContext(), SignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus history
        startActivity(intent)
    }
}

