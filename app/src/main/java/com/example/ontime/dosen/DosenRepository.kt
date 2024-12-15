package com.example.ontime.dosen

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DosenRepository {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = auth.currentUser?.uid

    private val _dosens = MutableStateFlow<List<Dosen>>(emptyList())
    val dosens: StateFlow<List<Dosen>> get() = _dosens

    init {
        // Listen to data changes in Firebase
        currentUserId?.let { uid ->
            val dosenRef = database.getReference("dosens/$uid")

            dosenRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedNotes =
                        snapshot.children.mapNotNull { it.getValue(Dosen::class.java) }
                    _dosens.value = fetchedNotes
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }
    }


    fun insertDosenRep(dosen: Dosen) {
        currentUserId?.let { uid ->
            val dosenRef = database.getReference("dosens/$uid")

            dosen.id = dosenRef.push().key
            dosen.id?.let {
                dosenRef.child(it).setValue(dosen)
            }
        }
    }

    fun deleteDosenRep(dosen: Dosen) {
        currentUserId?.let { uid ->
            val dosenRef = database.getReference("dosens/$uid")
            dosen.id?.let { dosenRef.child(it).removeValue() }
        }
    }


}